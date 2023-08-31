# [Choice] Debian OS version: bullseye, buster
ARG VARIANT
FROM mcr.microsoft.com/devcontainers/python:3-${VARIANT}
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y \
        gawk \
        wget \
        git-core \
        subversion \
        diffstat \
        unzip \
        sysstat \
        texinfo \
        build-essential \
        chrpath \
        socat \
        xz-utils  \
        locales \
        cpio \
        screen \
        tmux \
        sudo \
        iputils-ping \
        python3-pexpect \
        python3-git \
        python3-jinja2 \
        libegl1-mesa \
        libsdl1.2-dev \
        pylint \
        xterm \
        iproute2 \
        lz4 \
        blt \
        libtcl8.6 \
        zstd && \
    case ${TARGETPLATFORM} in \
        "linux/amd64") \
            DEBIAN_FRONTEND=noninteractive apt-get install -y \
                gcc-multilib \
                g++-multilib \
            ;; \
    esac && \
    /usr/sbin/locale-gen en_US.UTF-8 && \
    echo 'dash dash/sh boolean false' | debconf-set-selections && \
    DEBIAN_FRONTEND=noninteractive dpkg-reconfigure dash