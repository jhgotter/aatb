#/bin/bash
if [ $# -eq 0 ]; then
/Tools/adt-bundle-linux-x86_64-20140702/sdk/tools/emulator -avd CHTSOC_4.1.2 &
elif [ $# -gt 0 ] && [ "$1" == "proxy" ] ; then
/Tools/adt-bundle-linux-x86_64-20140702/sdk/tools/emulator -avd CHTSOC_4.1.2 -http-proxy 127.0.0.1:8080 &
fi
