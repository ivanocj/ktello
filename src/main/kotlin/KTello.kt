import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * Copyright ©  2018, Ivan Costa (http://github.com/ivanocj)
 * @author ivanocj@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */


class KTello {

    internal var ip: InetAddress? = null
        private set
    internal var port: Int = 0
        private set
    internal var socket: DatagramSocket? = null
        private set
    var isImperial: Boolean = false

    val isConnected: Boolean
        get() = if (null == socket) false else socket!!.isConnected

    /**
     *  Instrumentation Commands
     */


    val battery: String
        @Throws(IOException::class)
        get() = sendCommand("battery?")

    val speed: String
        @Throws(IOException::class)
        get() = sendCommand("speed?")

    val time: String
        @Throws(IOException::class)
        get() = sendCommand("time?")

    /**
     *  Connection Commands
     */

    @Throws(Exception::class)
    fun connect() {
        this.connect("192.168.10.1", 8889)
    }

    @Throws(Exception::class)
    fun connect(strIP: String, port: Int) {
        this.port = port
        ip = InetAddress.getByName(strIP)
        socket = DatagramSocket(port)
        socket!!.connect(ip!!, port)
        sendCommand("command")
        println(socket!!.localPort)
        println(socket!!.localSocketAddress)
        println(socket!!.localAddress)
        println(socket!!.port)
        println(socket!!.remoteSocketAddress)
        println(socket!!.reuseAddress)
        println(socket!!.receiveBufferSize)
        println(socket!!.sendBufferSize)
        println(socket!!.trafficClass)
    }

    /**
     *  Control Commands
     */

    @Throws(IOException::class)
    fun takeOff(): Boolean {
        return isOK(sendCommand("takeoff"))
    }

    @Throws(IOException::class)
    fun land(): Boolean {
        return isOK(sendCommand("land"))
    }

    /**
     * Fly up xx | xx = (20-500 cm)
     */
    @Throws(IOException::class)
    fun up(z: Int): Boolean {
        return isOK(sendCommand("up " + getDistance(z)))
    }

    /**
     * Fly down xx | xx = (20-500 cm)
     */
    @Throws(IOException::class)
    fun down(z: Int): Boolean {
        return isOK(sendCommand("down " + getDistance(z)))
    }

    /**
     * Fly left xx | xx = (20-500 cm)
     */
    @Throws(IOException::class)
    fun left(x: Int): Boolean {
        return isOK(sendCommand("left " + getDistance(x)))
    }

    /**
     * Fly right xx | xx = (20-500 cm)
     */
    @Throws(IOException::class)
    fun right(x: Int): Boolean {
        return isOK(sendCommand("right " + getDistance(x)))
    }

    /**
     * Fly forward xx | xx = (20-500 cm)
     */
    @Throws(IOException::class)
    fun forward(y: Int): Boolean {
        return isOK(sendCommand("forward " + getDistance(y)))
    }

    /**
     * Fly backward xx | xx = (20-500 cm)
     */
    @Throws(IOException::class)
    fun back(y: Int): Boolean {
        return isOK(sendCommand("back " + getDistance(y)))
    }

    /**
     * Rotate clockwise x° | x = (1-3600°)
     */
    @Throws(IOException::class)
    fun cw(x: Int): Boolean {
        return isOK(sendCommand("cw $x"))
    }

    /**
     * Rotate counter-clockwise xx° | xx = (1-3600°)
     */
    @Throws(IOException::class)
    fun ccw(x: Int): Boolean {
        return isOK(sendCommand("ccw $x"))
    }

    /**
     * Flip x l = (left) r = (right) f = (forward) b = (back) bl = (back/left) rb = (back/right) fl = (front/left) fr = (front/right)
     */
    @Throws(IOException::class)
    fun flip(direction: String): Boolean {
        return isOK(sendCommand("flip $direction"))
    }

    /**
     * Set current speed as xx | xx = (1-100 cm/s)
     */
    @Throws(IOException::class)
    fun setSpeed(speed: Int): Boolean {
        return isOK(sendCommand("speed $speed"))
    }

    /**
     * Internal functions
     */

    private fun getDistance(distance: Int): Int {
        return if (!isImperial) distance else Math.round((distance.toFloat() * 2.54).toFloat())
    }

    private fun isOK(strResult: String?): Boolean {
        return null != strResult && strResult == "OK"
    }

    @Throws(IOException::class)
    private fun sendCommand(strCommand: String?): String {
        if (null == strCommand || 0 == strCommand.length)
            return "empty command"
        if (!socket!!.isConnected)
            return "disconnected"
        val receiveData = ByteArray(1024)
        val sendData = strCommand.toByteArray()
        val sendPacket = DatagramPacket(sendData, sendData.size, ip, port)
        socket!!.send(sendPacket)
        val receivePacket = DatagramPacket(receiveData, receiveData.size)
        socket!!.receive(receivePacket)
        val ret = String(receivePacket.data)
        println("Tello $strCommand: $ret")
        return ret
    }

    fun close() {
        if (null != socket)
            socket!!.close()
    }

}