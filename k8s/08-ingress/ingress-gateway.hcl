Kind = "ingress-gateway"
Name = "ingress-gateway"

Listeners = [
 {
   Port = 8786
   Protocol = "http"
   Services = [
     {
       Name = "frontend"
     }
   ]
 }
]
