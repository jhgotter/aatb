#!/bin/bash
# $1: apkfile
# $2: destination folder
if [ $# == 3 ] ; then
slash='/'
jar='.jar'
#apktool
java -jar /Tools/decompiler/apktool_2.0.0rc2.jar d -d -f -o $2 $1
#dex2jar
/Tools/decompiler/dex2jar-0.0.9.15/d2j-dex2jar.sh -f -o $2$slash$3$jar $1
#jdgui
/Tools/decompiler/jd-gui-0.3.5.linux.i686/jd-gui $2$slash$3$jar
fi
