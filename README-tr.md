# Blog sitelerinden PostgreSQL Config Parametrelerinin çekilmesi

Bu repo [postgresqlco.nf ](https://postgresqlco.nf/doc/en/param/)'den ilham alınarak yapılmıştır. Blog yazılarında PostgreSQL Server parametrelerini arar, bir blog sayfasında bir parametre bulduğunda, bunu parametre, blog başlığı ve blog bağlantısıyla veritabanına yazar. Web servisi üzerinden parametre ile sorulduğunda JSON belgeleri olarak döndürür.

Bu servis 2 farklı modül içerir.
* Birincisi, belirli bir havuzdan blog URL'lerinin değerlerini alan ve özyineleyerek bloga erişir ve içinde PostgreSQL parametrelerinden biri veya birden fazlası varsa   üzerinden geçer ve veritabanına yazılmışsa, PostgreSQL parametrelerini arar.
* İkincisi, PostgreSQL parametrelerini alan ve istendiğinde ilgili blog yazılarını döndüren dinlendirici bir API'dir.

## Blog Paletli
Paletli modülü bir cron planlanmış ilkbahar parti işidir. Çaldığında havuza gider ve tüm blog sitelerini çeker. Sonra bu siteleri yineleyin ve özyinelemeli, derinlere gidin ve PostgreSQL parametresini arayın. Arama yapıldıktan sonra, Blog sitesi URL'si, PostgreSQL parametresini ve postanın başlığını veritabanına yazar. Bu iki işlem, gerdirme API'si değil, toplu işler olarak çalışır. Donanım kapasitesi nedeniyle, tarama derinlik sınırlıdır.

### ayar cron config

Cron konfigürasyonu veritabanında tutulur. Uygulama oluşturduğunda veritabanına gider ve CRON yapılandırmasını alır. Her çalıştırıldıktan sonra, veritabanına gider ve Cron Config'ü sıfırlar. Böylece her bir yinelemeden sonra CRON Config'ini değiştirebilirsiniz.

* Cron Config Cron_CONF tablosunda tutar.
* Satırı 1'e eşit bir tuşla alır. Bu yüzden yeni bir satır eklemeyin, sadece mevcut satırı güncelleyin.
<br />
Örnekler:
<br />
`Cron_conf güncelleme cron_exp = '0 * / 1 * * * *', burada id = 1;`

### dinlendirici API
Son nokta JWT belirteci doğrulandı, bu nedenle her kullanıcının JWT_TOKEN tablosunda benzersiz bir JWT olmalıdır. Bir PostgreSQL parametresini alır ve ilgili blog yazılarını döndürür. Uygulamanın bir swagger konfigürasyonu vardır, bu nedenle uygulama swagger'da test edilebilir.

### önceden yüklenmiş veri
Veriler daha önce sürünür ve veritabanına yüklenir. Bu verileri kullanmak istiyorsanız, veritabanınıza yükleyebilirsiniz.

### Nasıl paketlenir
`MVN Temiz Paketi Yay-Boot: Repackage -DsKiptests`

### nasıl çalıştırılır
`java -jar blogcrawling-0.0.1-snapshot.jar`

### Maven ile nasıl çalıştırılır
`MVN Temiz Bahar Boot: Run`
