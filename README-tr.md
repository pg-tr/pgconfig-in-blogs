# Blog sitelerinden PostgreSQL Config Parametrelerinin çekilmesi

Bu repo [postgresqlco.nf ](https://postgresqlco.nf/doc/en/param/)'den ilham alınarak yapılmıştır. Blog yazılarında PostgreSQL Server parametrelerini arar, bir blog sayfasında bir parametre bulduğunda, bunu parametre, blog başlığı ve blog bağlantısıyla veritabanına yazar. Web servisi üzerinden parametre ile sorulduğunda JSON belgeleri olarak döndürür.

Bu servis 2 farklı modül içerir.
* Birincisi, belirli bir havuzdan blog URL'lerinin değerlerini alır ve bloga erişir ve içinde PostgreSQL parametrelerinden biri veya birden fazlası varsa veritabanına yazar.
* İkincisi, PostgreSQL parametrelerini GET isteği ile alan ve ilgili blog yazılarını JSON olarak dönen bir API'dir.

## Blog Crawling
Crawling modülü bir spring batch işlemidir. Çalıştığında havuzdan listeyi çeker ve tüm blog sitelerini gezer ve PostgreSQL parametrelerini arar. Arama yapıldıktan sonra, Blog sitesi URL'si, PostgreSQL parametresini ve blogun başlığını veritabanına yazar. Donanım kapasitesi nedeniyle, tarama derinliği sınırlanmıştır.

### cron config

Cron konfigürasyonu veritabanında tutulur. Uygulama ayağa kalktığında veritabanına gider ve CRON yapılandırmasını alır. Her çalıştırıldıktan sonra, veritabanına gider ve Cron Config'i sıfırlar. Böylece her bir çalışmasından sonra cron config'ini değiştirebilirsiniz.

* Cron Config cron_conf tablosunda tutar.
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
