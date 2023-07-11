import java.net.Socket
import java.net.ServerSocket
import kotlin.concurrent.thread
import java.io.InputStream
import java.io.OutputStream

fun handleClient(clientSocket: Socket) {
    val request = clientSocket.getInputStream().bufferedReader().readLine()
    val firstLine = request.substringBefore("\r\n")
    val method = firstLine.split(" ")[0]
    println("Request method: $method")
    println("Request line: $firstLine")

    if (method == "CONNECT") {
        // Extract the requested host and port
        val hostPort = firstLine.split(" ")[1]
        val (host, port) = hostPort.split(":")

        // Create a connection to the requested server
        val serverSocket = Socket(host, port.toInt())

        // Send the client a success response//
        //clientSocket.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".toByteArray())

        // Start forwarding data between client and server
        thread { forwardData(clientSocket.getInputStream(), serverSocket.getOutputStream()) }
        thread { forwardData(serverSocket.getInputStream(), clientSocket.getOutputStream()) }
    } else {
        // Handle regular HTTP requests
        val host = firstLine.split(" ")[1].removePrefix("http://").removeSuffix("/")
        println("Request host: $host")

        // Create a connection to the target server on port 80
        val serverSocket = Socket(host, 80)

        println("Connected to $host")

        // Forward the client's request to the server
        serverSocket.getOutputStream().write(request.toByteArray())

        // Wait for a second before forwarding the data
        Thread.sleep(1000)

        // Forward the server's response back to the client
        thread { forwardData(serverSocket.getInputStream(), clientSocket.getOutputStream()) }
    }
}

fun forwardData(sourceStream: InputStream, destinationStream: OutputStream) {
    val buffer = ByteArray(8192)
    var bytesRead: Int
    while (sourceStream.read(buffer).also { bytesRead = it } != -1) {
        destinationStream.write(buffer, 0, bytesRead)
    }
}

fun startProxyServer() {
//val proxyServer = ServerSocket(8001)
  while (true) {
    println("Proxy server listening on port 8001")
        val clientSocket = Socket("127.0.0.1", 3000)
        val clientAddress = clientSocket.remoteSocketAddress
        println("Received connection from $clientAddress")
        thread { handleClient(clientSocket) }

}
}

fun main() {
    startProxyServer()
}
