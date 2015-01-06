#!/bin/bash
/Tools/adt-bundle-linux-x86_64-20140702/sdk/platform-tools/adb -s $1 forward tcp:31415 tcp:31415
#drozer console connect
