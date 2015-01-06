result = `/Tools/adt-bundle-linux-x86_64-20140702/sdk/build-tools/21.1.2/aapt dump badging #{ARGV[0]} | grep package | awk '{print $2}'`.split("\'")
puts result[1]
