# Android test

Zahteve naloge:
- Prikaz seznama mobilnih aplikacij, ki jih pridobite iz našega API-ja (API je opisan v nadaljevanju).
- Prikaz podatkov naj podpira pridobivanje podatkov po straneh (API vrača po 5 aplikacij na stran)
- Za vsako aplikacijo v seznamu prikažite osnovne podatke (slika in naziv)
- Prikaz podrobnosti aplikacije (na novem zaslonu prikažite vse podatke, ki ste jih pridobili iz API-ja za izbrano aplikacijo, vključno s sliko)
- Omogočite prenos slike na mobilno napravo v ozadju
- Pri integraciji API-ja upoštevajte, da ti ni pretestestiran, zato je potrebno predvideti in upoštevati/prikazati napake, ki jih lahko vrne API.

Dodatne funkcionalnosti (**opcijsko**):
- Seznam aplikacij, ki jih pridobite iz API-ja, se naj prikazujejo v dveh stolpcih.
- Seznam aplikacij naj omogoča iskanje in sortiranje po nazivu
- Seznam aplikacij naj omogoča filtriranje (prikaz vseh aplikacij, prikaz aplikacij glede na platformo - Android in iOS)
- Aplikacija se naj prikazuje v dveh jezikih (angleščina in slovenščina, prikaz glede na izbran jezik naprave)
- Omogočite enostavno izgradnjo dveh različic aplikacije (različna barvna shema, ikoni in naziva aplikacije)
- Unit testi za poljubno metodo (naj ima vsaj 3 možne scenarije)
- Animacije


Pri izdelavi aplikacije sledite dobrim praksam programiranja, kot so uporaba stilov in arhitekturna razdelitev kode. Pri izgledu aplikacije vam prepuščamo proste roke, saj nas zanima tudi vaš občutek za design. 

--------------------------------------------------------------------------------------

API call:
https://zpk2uivb1i.execute-api.us-east-1.amazonaws.com/dev/data?page={page_number}

--------------------------------------------------------------------------------------

Request example:

GET https://zpk2uivb1i.execute-api.us-east-1.amazonaws.com/dev/data?page=1

--------------------------------------------------------------------------------------

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
