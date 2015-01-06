result = `sha256sum #{ARGV[0]} | awk '{print $1}'`.chop.to_s
puts result
