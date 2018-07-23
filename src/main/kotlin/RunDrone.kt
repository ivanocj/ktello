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

/**
 * !!! READ THIS NOW !!!
 * Before running code using this wrapper
 * please make sure your computer is connected to
 * Tello's WIFI using IP's 192.168.10.X
 * where X > 1 and/or has DHCP enabled.
 *
 **/
fun main(args: Array<String>) {
    val tello = KTello()
    tello.connect()
    Thread.sleep(3000)
    if (tello.isConnected) {
        tello.takeOff()
        Thread.sleep(10000)
        tello.land()
    }
    tello.close()
}