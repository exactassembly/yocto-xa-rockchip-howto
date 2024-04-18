
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " file://00_rockusb.cfg"


do_deploy:append:px30() {
    install -D -m 644 ${B}/spl/u-boot-spl.bin ${DEPLOYDIR}/u-boot-spl-${MACHINE}-${PV}-${PR}.bin
    install -D -m 644 ${B}/tpl/u-boot-tpl.bin ${DEPLOYDIR}/u-boot-tpl-${MACHINE}-${PV}-${PR}.bin

    cd ${DEPLOYDIR}
    ln -sf u-boot-tpl-${MACHINE}-${PV}-${PR}.bin u-boot-tpl-${MACINE}.bin
    ln -sf u-boot-tpl-${MACHINE}-${PV}-${PR}.bin u-boot-tpl.bin
    ln -sf u-boot-spl-${MACHINE}-${PV}-${PR}.bin u-boot-spl-${MACHINE}.bin
    ln -sf u-boot-spl-${MACHINE}-${PV}-${PR}.bin u-boot-spl.bin
}