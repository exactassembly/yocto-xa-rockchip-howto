arch=arm
baudrate=115200
board=evb_px30
board_name=evb_px30
bootdelay=2
cpu=armv8
cpuid#=55524b5732363030390000000018181a
bootimg=Image
fdt_addr_r=0x08300000
loadaddr=0x800800
fdtimage=px30-evb.dtb
bootcmd=run mmcload; run mmcboot
mmcbootpart=6
mmcloadcmd=fatload
mmcload=mmc rescan;${mmcloadcmd} mmc 0:${mmcbootpart} ${loadaddr} ${bootimg};${mmcloadcmd} mmc 0:${mmcbootpart} ${fdt_addr_r} ${fdtimage}
mmcboot=setenv bootargs "earlycon=uart8250,mmio32,0xff160000 swiotlb=1 console=ttyS2,115200n8 rw root=/dev/mmcblk2p7 rootfstype=ext4 rootwait"; booti ${loadaddr} - ${fdt_addr_r}