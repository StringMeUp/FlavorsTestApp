This is a simple representation of android flavors with "manual" pagination. 
It has a details screed where unsurprisingly more details can be found with the 
functionality to download an image on tap. Other features include also a search option
and simple flavors for phone and tablet. DI was not used as it was a rather small app and I 
wanted to leverage Kotlin Objects. Hilt would be a great option in future. 

P.S. The backed randomly snaps since it was made to work that way obviously. (error handling purposes)
This is in case you want to take a look at the ui, which isn't that bad I must say. 

Response example:
```json
{
    "page":0,
    "max_pages":2,
    "items":[
        {
            "type":"android",
            "name":"Cankarjev dom",
            "image":"https://tehnik.telekom.si/PublishingImages/slovenske-aplikacije/Cankarjev-dom.png",
            "android_store":"https://play.google.com/store/apps/details?id=cd.cc&hl=sl",
            "android_details":{
                "ratings":17,
                "score":4.5
            }
        },
        {
            "type":"ios",
            "name":"Daljinec+",
            "image":"https://tehnik.telekom.si/PublishingImages/slovenske-aplikacije/DaljinecPlus.png",
            "ios_store":"https://itunes.apple.com/si/app/daljinec+/id639982670?mt=8",
            "ios_details":{
                "score":2.4,
                "size":46.6
            }
        }
    ]
}
```
