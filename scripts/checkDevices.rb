#!/usr/bin/ruby
result = `/Tools/adt-bundle-linux-x86_64-20140702/sdk/platform-tools/adb devices | grep device$`
puts result
