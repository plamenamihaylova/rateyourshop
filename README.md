# Rate your shop
<p> Internet introduced online shopping and the online customer experience is evolving ever since. <br>
Although, the most popular online shoping platforms have ratings section where people can make reviews, 
there are still many smaller online shops that are missing this feature which is so valuable for the customer. </p>
<p> "Rate your shop" system will provide custemomers with the ability to rate their online shopping experience. <br>
Rating data would be available for everyone, but only registered users would be able to write a review, rate and add an online shop. <br>
Admin users will manage registered users, shops and respond to suggestion for adding new online store in the system. </p>
<p> The system is developed using <strong>Spring Boot 2.<strong><br>
The backend is implemented as a REST/JSON API using JSON data serialization. </p>

# API Resources
URI | Methods
------------ | -------------
/api/v1/users | GET, POST
/api/v1/users/{id} | GET, PUT, DELETE
/api/v1/users/?name=firstName; ?username=username; ?role=role | GET, PUT, DELETE
/api/v1/login | POST
/api/v1/logout | POST
/api/v1/shops | GET, POST
/api/v1/shops/{id} | GET, PUT, DELETE
/api/v1/shops/name/{name} | GET, PUT, DELETE
/api/v1/shops/?category=category | GET
/api/v1/reviews | GET, POST
/api/v1/reviews/{id} | GET, PUT, DELETE
/api/v1/reviews/?user=userId | GET
/api/v1/shops/{shopId}/reviews/{reviewId} | GET
/api/v1/shops/{shopId}/reviews/{reviewValue} | GET
