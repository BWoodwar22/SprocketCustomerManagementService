# SprocketCustomerManagementService
REST service for managing sprocket customers, placing orders, and viewing who has ordered the most sprockets.

End points:  
/customers --View/add customers (accepts GET and POST)  
/customers/{id} --View/update a customer (accepts GET and PUT)  
/customers/{id}/orders --View orders made by a customer (accepts GET and takes optional 'to' and 'from' date params)  
/analytics/customers/ordered-most --Get summary info about who has ordered the most (accepts GET and takes optional 'to' and 'from' date params)  

Example:  
/customers/1/orders?from=2018-01-01T00:00:00

Date format expected is ISO 8601 date_time or yyyy-MM-dd'T'HH:mm:ss.SSSXXX.
