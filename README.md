# Salom yaxshi insonlar

Ushbu loyihada men o'yin yaratish uchun eng yaxshi piksellarni boshqarish imkonini beruvchi
texnologiyani aniqlashga harakat qilib ko'raman. Shuningdek, test natijalarini siz bilan baham ko'raman.

Test natijalari har xil qurilmalarda har xil chiqadi, shuning uchun sizga qiziq bo'lsa,
siz ham sinov o'tkazib ko'ring va natijasini men bilan baham ko'rishingizni xohlayman.
Albatta, siz yuborgan natijalarni ushbu github omborimga joylashtiraman, chunki bizga aniq natija kerak.

## Sinov maqsadi

Sinov maqsadi o'yin yaratishda grafikalar bo'yicha qaysi biri tezkorroq ekanligini aniqlash,
fps qiymatini normal holatda saqlab turish va barcha sinov natijalariga qarab dasturchilar
qaysi birini tanlashi kerakligini aniqlashga yordam berishdan iborat. Buning uchun pythonda ham
Androidda ham shu sinovni amalga oshiraman.
Bu loyiha Android uchun, [python loyihalari uchun ustiga bosing](https://github.com/khalilovibrohimuz/GraphTestForPython).
Agar sinov muvaffaqiyatli deb baholansa, u orqali bir nechta namunaviy loyihalar
chiqaraman (balki, alohida loyiha yaratishim ham mumkin, shunday qilsam havola qoldriaman).

## Sinovni boshlash

Pythondagi testdan farqli o'laroq Androidda console mavjud emas, Logcat esa juda ko'p xabar
chiqarganligi bois natijani olish uchun noqulaylik qiladi. Shuning uchun har bir texnologiya uchun
alohida activity yaratamiz va natijalarini ekranga chiqaramiz.
Test natijalarini skrinshot olib githubga yuklayman va havolalar qoldiraman.
Sinov to'liq ekran rejimida bo'ladi, bunda displey 720x1600 o'lchamga ega bo'ladi
(sizni displeyingiz farqli bo'lishi mumkin). Androidda FPS 60 dan oshmasligini hisobga olib,
imkon qadar 60 FPS ga yetadigan darajada tezlashtirishga harakat qilaman

## Piksellarni manipulatsiya qilish texnologiyalari

Canvas har qanday viewlarda mavjud bo'lgan chizishda ishlatiladigan klass.
Usiz displeyga hech qanday narsani tasvirlab bo'lmaydi, shunday ekan biz undan foydalanishga majburmiz.
1. Canvas'ni o'zida drawPoint() orqali piksellarini manipulatsiya qilishimiz mumkin
Canvas'ni o'zida bu ish sekinroq amalga oshadi, lekin bitmapga o'zgartirishlar kiritib
canvasga chiqarish uni anchagina tezlashishiga olib keladi
2. Bitmap'ga setPixel() funksiyasi orqali manipulatsiya qilish va canvasga chizish
Bitmapdagi setPixel() funksiyasi anchagina sekin ishlaydi, lekin hamma piksellarini bittada
setPixels orqali o'zgartirib yuborsak tezlashadi. Lekin bizga matrix degan bir o'lchamli massiv kerak.
Shunda biz matrix massivini piksellarini manipulatsiya qilib bitmapga berib yuboramiz
3. matrix'dagi piksellarni manipulatsiya qilish, bitmapga o'tkazish va canvasga chizish
Navbatdagi usullarda NDK ishlatiladi. C++ da piksellarni manipulatsiya qiluvchi funksiya yaratamiz
4. C++'da bitmapga ishlov berish uchun pixel(bitmap, x, y, color) funksiyasini yaratish
Endi yuqoridagi C++ kodni optimal variantidan foydalanamiz, bunda bitmap bloklanadi,
o'zgartirishlar kiritiladi, bitmap blokdan chiqariladi, oxirida canvas orqali ekranga tasvirlanadi
5. C++'da lock(bitmap), pixel(x, y, color) va unlock() funksiyalari yaratiladi
Bu ishni amalga oshirishda SurfaceView yoki View ni parent class qilib olish mumkin.
Men SurfaceViewdan foydalanishni tavfsiya qilmayman (sinovda uni ham natijasini ko'rsatib beraman).
Chunki uning rang chuqurligi yetarli darajada emas.
Bizga 32 bit chuqurlikdagi tasvirni ekranga to'liqligicha tasvirlay oladigan usul View'ni
parent qilib olish va Thread orqali o'yin tsiklini tashkil qilishimiz mumkin bo'ladi

## Test natijalari


Test natijalari bilan quyidagi manzil orqali tanishishingiz mumkin:

- [Barcha test natijalari](https://github.com/khalilovibrohimuz/GraphTestForAndroid/tree/master/tests)
- [Eng yaxshi test natijasi](https://github.com/khalilovibrohimuz/GraphTestForAndroid/blob/master/tests/1stTest.md)
<hr>
![Test natijasiga misol](https://github.com/khalilovibrohimuz/GraphTestForAndroid/blob/master/app/src/main/assets/activity_cpp_release.jpg)

## Maslahatlar


O'yin yaratishda displeyni tezroq boshqarish va optimizatsiya qilish uchun quyidagi maslahatlarni beraman:

- Piksellari o'zgarmaydigan joylar uchun qiymatlarni o'zgartirishdan qoching.
- Tezlikni oshirish uchun displey o'lchamini kichraytirish ham foydali bo'lishi mumkin
(pixel o'lchami 2 barobar kattalashsa FPS 4 barobar tezlasha oladi).
- Barcha o'zgarmaydigan layerlarni o'yin boshlanishidan oldin yaratib oling.
- Loyihangiz uchun AssetManager klassini yaratib olishingiz mumkin,
hamma rasmlarni o'sha yerni o'zida yaratib unga ishlov bersangi bo'ladi.
- Tezroq bajarilishi kerak bo'lgan funksiyalarni C++ da yaratib Kotlinda chaqirib,
ishlatish kodni qariyb 3 barobar tezlashishiga olib keladi
- for yoki while tsikllariga murojaat qilganingizda e'tiborli bo'ling,
uni ichida obyekt qayta qayta yaratilishiga yo'l qo'ymang.

## Keyingi qadamlar

Sizga algoritmingizni optimallashtirish haqida ko'p gapirdim.
Ammo bu siz uchun tushunarsiz bo'layotgani tabiiy hol.
shuning uchun amalda sizga buni qanday qilish kerakligini ko'rsatib berishga harakat qilaman.
O'yin yaratishda bizga grafikani o'zi yetarli emas, shunday ekan, piksellarni samarali
boshqarish texnologiyasini (piksellar o'zgarmaydigan qismi uchun o'zgarish amalga oshirmaslik),
ekran yangilanish tezligi(FPS)ni normal ishlab turish va undan foydalanib dastur yaratish
borasida bir nechta misollar yarataman.
Siz esa men keltirgan misollarni o'rganib chiqib buni qanday amalga oshirishni tushunib olishingiz mumkin ;)
