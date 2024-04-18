COMPATIBLE_MACHINE:xa-rockchip-px30-evb = "xa-rockchip-px30-evb"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://debug-uart.patch"
SRC_URI += "file://00_ethernet.cfg"