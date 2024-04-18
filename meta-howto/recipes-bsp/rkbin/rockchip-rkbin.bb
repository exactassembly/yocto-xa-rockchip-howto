# TODO
# pull rkbin repo with specific rev
# copy files into repo at proper locations
# make a means of selecting uart port, mux, and baudrate for ddr params
# use ddr tool to fix ddr using ddr_param.txt
# generate loader using loader-config.ini and tools/rkdeveloptool
# deploy loader into ${DEPLOYDIR} using deploy class and do_deploy

DESCRIPTION = "Rockchip Firmware and Tool Binaries"
LICENSE = "Poprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=15faa4a01e7eb0f5d33f9f2bcc7bff62"

SRC_URI = "git://github.com/rockchip-linux/rkbin;protocol=https;branch=master"
SRCREV = "a2a0b89b6c8c612dca5ed9ed8a68db8a07f68bc0"

DEPENDS += " \
    coreutils-native \
    virtual/bootloader \
    "

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://px30_loader_config.ini;subdir=git"
SRC_URI += "file://px30_usbplug_v1.20.bin;subdir=git/bin/rk33"
SRC_URI += "file://px30_ddr_param.txt;subdir=git"

PROVIDES += "rockchip-loader"

inherit bin_package deploy

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE:px30 = "px30"

PACKAGE_ARCH = "${MACHINE_ARCH}"

unset do_configure[noexec]
unset do_compile[noexec]

RKBIN_DDR_UART_ID ?= "2"
RKBIN_DDR_UART_IOMUX ?= "1"
RKBIN_DDR_UART_BAUDRATE ?= "115200"

RKBIN_DDR_PATH ?= "bin/rk33/px30_ddr_333MHz_v2.09.bin"
RKBIN_USBPLUG_PATH ?= "bin/rk33/px30_usbplug_v1.36.bin"

do_configure() {
    # add config files in here as well as making uart ddr selections
    # maybe also run ddr_tool here?
    
    # copy over u-boot files
    cp ${DEPLOY_DIR_IMAGE}/u-boot-spl.bin ${S}
    cp ${DEPLOY_DIR_IMAGE}/u-boot-tpl.bin ${S}

    # config ddr uart
    cd ${S}
    sed -e 's/\(uart id=\).*/\1${RKBIN_DDR_UART_ID}/' -e 's/\(uart iomux=\).*/\1${RKBIN_DDR_UART_IOMUX}/' -e 's/\(uart baudrate=\).*/\1${RKBIN_DDR_UART_BAUDRATE}/' <px30_ddr_param.txt> ddr_param.txt

    # config loader usbplug and ddr binaries
    sed -e '/\[CODE471/,/\(Path1=\)/s^\(Path1=\).*^\1${RKBIN_DDR_PATH}^' -e '/\[CODE472/,/\(Path1=\)/s^\(Path1=\).*^\1${RKBIN_USBPLUG_PATH}^' px30_loader_config.ini > config.ini

    # configure ddr using px30_ddr_param.txt
    ./tools/ddrbin_tool px30 ddr_param.txt ${RKBIN_DDR_PATH}
}

do_compile() {
    cd ${S}

    # fixup u-boot spl and tpl
    printf "RK33" | dd conv=notrunc bs=4 count=1 of=u-boot-tpl.bin
    truncate -s %2048 u-boot-tpl.bin
    truncate -s %2048 u-boot-spl.bin

    # run boot_merger to create loader 
    ./tools/boot_merger config.ini
}

do_install() {
    :
}

PACKAGES = "${PN}"
ALLOW_EMPTY:${PN} = "1"

do_deploy() {
    # deploy loader to deploy
    install -D -m 644 ${S}/px30_loader_uboot.bin ${DEPLOYDIR}
}

addtask deploy after do_install