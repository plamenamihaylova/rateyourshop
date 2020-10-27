# rate_your_shop
Internet introduced online shopping and the online customer experience is evolving ever since.
Although, the most popular online shoping platforms have ratings section where people can make reviews, 
there are still many smaller online shops that are missing this feature which is so valuable for the customer.
"Rate your shop" system will provide custemomers with the ability to rate their online shopping experience.
Rating data would be available for everyone, but only registered users would be able to write a review, rate and add an online shop.
Admin users will manage registered users, shops and respond to suggestion for adding new online store in the system.
The system will be developed using Spring 5 Application Development Framework.
The backend will be implemented as a REST/JSON API using JSON data serialization.

# API Resources
/api/v1/users - for administrators only - GET, POST
/api/v1/users/{id} - GET, PUT, DELETE
/api/v1/users/?name=firstName | ?username=username | ?role=role – for administrators only - GET, PUT, DELETE

/api/v1/login - POST
/api/v1/logout - POST

/api/v1/shops - for administrators only - GET, POST
/api/v1/shops/{id} - for administrators only - GET, PUT, DELETE
/api/v1/shops/name/{name} - for administrators only - GET, PUT, DELETE
/api/v1/shops/?category=category – GET

/api/v1/reviews - GET, POST
/api/v1/reviews/{id} - GET, PUT, DELETE
/api/v1/reviews/?user=userId - GET
/api/v1/shops/{shopId}/reviews/{reviewId}
/api/v1/shops/{shopId}/reviews/{reviewValue}
