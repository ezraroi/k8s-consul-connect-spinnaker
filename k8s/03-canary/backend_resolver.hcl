kind = "service-resolver"
name = "backend"

# https://www.consul.io/api/health.html#filtering-2
# Show Node.Meta demonstration showing performance testing a new instance type
default_subset = "blue"

subsets = {
  blue = {
    filter = "Service.Meta.version == 1"
  }
  green = {
    filter = "Service.Meta.version == 2"
  }
}