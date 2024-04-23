COMPATIBLE_MACHINE:firefly-px30 = "firefly-px30"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"


SRC_URI += "file://00_ethernet.cfg"
SRC_URI += "file://firefly-px30.dts;subdir=git/arch/${ARCH}/boot/dts/rockchip"
PACKAGE_ARCH = "${MACHINE_ARCH}"