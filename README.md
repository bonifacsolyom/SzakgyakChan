# Üzleti Igények

A SzakgyakChan egy olyan image board webalkalmazás, melyen a felhasználók különböző témákat beszélhetnek meg szabadon. A program felépítésben és funkcionálisan is hasonlít a klasszikus image board-okra, néhány kivétellel. A különzöző témájú diszkussziókat board-ok különítik el, melyek között a felhasználó a főoldalon tud választani. A boardok előre definiáltak, egy felhasználó nem tud újat létrehozni. A boardokon belül a felhasználó szabadon indíthat thread-eket tetszőleges, a board témájához kapcsolódó tartalommal, melyhez bármelyik felhasználó kommentelhet. Követelmény, hogy minden thread képpel és szöveggel kell, hogy kezdődjön, az azokhoz fűzött kommenteknél a kép hozzáfűzése opcionális, azonban mindenképpen egy lehetőség. A board-okon belül a threadek mindig olyan sorrendben vannak listázva, hogy melyikhez érkezett legutoljára komment, kivéve, ha a komment olyan opcióval lett elküldve, hogy az ne számítson bele ebbe - ezt a komment mező alatt egy checkbox teszi lehetővé.

A program a tradícionális image board-októl a következő módokban tér el: a SzakgyakChan tartalmaz felhasználókezelést, azaz sok más image board-dal ellentétben a threadindítás és kommentelés nem anonim, hanem kötelezően belépés után van csak rá lehetőség. A felhasználó tudja törölni a saját kommentjeit, azonban egy thread csak akkor törölhető, ha ahhoz egy komment sem tartozik. Ha egy felhasználó egy olyan kommentet töröl, melyhez egy kép is csatolva van, akkor a képfájl a szerverről is kötelezően törlődik. A program tartalmaz role kezelést, tehát egy felhasználó rendelkezhet user vagy adminisztrátori jogokkal - az utóbbi esetben bármilyen hozzászólást vagy threadet jogában áll letörölni.

A SzakgyakChan másik nagy feature-je, mely megkülönbözteti a hagyományos image board-októl, az a kommentekre való szavazási lehetőség. A kommentek mellett található egy szám, mely a szavazatok számát mutatja, illetve egy fel és egy lefelé mutató nyíl, melyek segítségével a felhasználó szavazhat a kommentre. Minden kommentre csak egy szavazatot küldhet egy felhasználó (vagy fel, vagy le), de természetesen maga a szavazás opcionális. Lehetőség van a threadek alatti kommentek kronológikus, illetve szavazat alapján való rendezésére is.

A felhasználó értesítést kap, ha egy kommentjére szavazat érkezik, illetve ha valaki válaszol rá. Ezt az értesítést látja a felhasználói felület egy részén, és addig highlight-olva van, amíg a felhasználó interakcióba nem lép vele. 

## Követelménylista:
| ID | Fontosság | Követelmény |
|-:|-:|-|
| 1 | magas | A felhasználó tud új fiókot regisztrálni |
| 2 | magas | A felhasználó be tud lépni a fiókjába |
| 3 | magas | A főoldal kilistázza a board-okat |
| 4 | magas | A board-okon belül egy belépett felhasználó tud threadeket létrehozni |
| 5 | magas | A thread-eket mindig képpel és szöveggel kell indítani |
| 6 | magas | A thread-ekhez egy belépett felhasználó tud kommentet fűzni |
| 7 | magas | A kommentekhez lehet képet fűzni, de nem kötelező |
| 8 | közepes | Mindig az a thread kerül előbb listázásra, amihez legutóbb érkezett komment |
| 9 | alacsony | Lehet olyan kommentet küldeni, melytől a thread nem kerül feljebb a listában |
| 10 | közepes | Van adminisztrátori és rendes user role |
| 11 | közepes | Belépett felhasználó letörölheti a saját kommentjeit |
| 12 | közepes | Egy thread csak akkor törölhető az azt posztoló user által, ha ahhoz nem tartozik több komment |
| 13 | közepes | Az adminisztrátori role bármilyen kommentet vagy threadet letörölhet |
| 14 | alacsony | Ha egy olyan komment/thread törlődik, melyhez kép tartozott, akkor a képfájl a szerverről is törlődjön |
| 15 | közepes | A kommentekre lehet szavazni, fel vagy lefelé |
| 16 | közepes | Egy kommentre egy user csak egy szavazatot küldhet |
| 17 | közepes | A kommentek mellett egy szám mutatja, hogy a komment hány szavazattal rendelkezik |
| 18 | alacsony | A kommenteket lehet kronológikusan, vagy szavazat alapján rendezni |
| 19 | közepes | A felhasználó értesítést kap, ha szavazat vagy válasz érkezik egy kommentjére |
| 20 | alacsony | Az értesítés addig aktív, amíg a felhasználó interkacióba nem lép vele |


# Tervek

A boardok tartalmazzák a threadeket, azok pedig a reply-okat. A threadekhez és a replyokhoz eltároljuk, hogy melyik user posztolta őket, ez a törlésnél hasznos. A képeknek készítünk egy külön image osztályt, így könnyebb reprezentálni olyan kommenteket, melyekhez nem lett kép csatolva. A felhasználó jelszavát természetesen nem plain textben tároljuk el, hanem valami hashelést végzünk - azonban még célszerűbb lehet, ha erre valami libraryt használunk majd. A threadeket érdemes úgy implementálni, hogy ők maguk nem tárolnak külön képet és textet, hiszen akkor a kommentek funkcionalitását valósítanánk meg mégegyszer - tehát a threadekben mindig lesz egy kezdő komment, ez fogja tartalmazni a threadnek szövegét és képét. Természetesen itt enforszolni kell, hogy ezen komment mindig tartalmazzon képet. Minden usernek van egy notifications tömbje, ebben tároljuk az eddigi összes értesítéseit - ha a notifikáció aktív, akkor a read flagje false. Ebből leszármazik két különböző értesítéstípus, amely egy új szavazatról, illetve egy új válaszról értesít. Ezeknek a különböző prezentációjuk miatt kell, hogy két külön osztály legyenek.

## Use-case diagram

![Use-case](./specifikacio/uml/use-case.svg)

## Entityk class-diagramja

![Class-diagram](./specifikacio/uml/class-diagram.svg)

## Szekvencia-diagramok

* Új thread posztolása:

![Thread posztolása](./specifikacio/uml/sequence1.svg)

* Boardok threadjeinek lekérése megjelenítéshez:

![Boardok megjelenítése](./specifikacio/uml/sequence2.svg)

* Komment értesítés küldése a felhasználónak

![Értesítés küldése](./specifikacio/uml/sequence3.svg)