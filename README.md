# Archive

Архива (35 поени) Problem 2 (2 / 3)<br/>
Да се имплементира класа ArchiveStore во која се чува листа на архиви (елементи за архивирање).

Секој елемент за архивирање Archive има:

* **id** - цел број<br/>
* **dateArchived** - датум на архивирање.<br/>

Постојат два видови на елементи за архивирање:<br/>
**LockedArchive** за кој дополнително се чува датум до кој не смее да се отвори **dateToOpen** и <br/>
**SpecialArchive** за кој се чуваат максимален број на дозволени отварања **maxOpen**.<br/>

За елементите за архивирање треба да се обезбедат следните методи:

* **LockedArchive(int id, Date dateToOpen)** - конструктор за заклучена архива
* **SpecialArchive(int id, int maxOpen)** - конструктор за специјална архива

За класата ArchiveStore да се обезбедат следните методи:

* **ArchiveStore()** - default конструктор
* **void archiveItem(Archive item, Date date)** - метод за архивирање елемент item на одреден датум date
* **void openItem(int id, Date date)** - метод за отварање елемент од архивата со зададен id и одреден датум date.
Ако не постои елемент со даденото id треба да се фрли исклучок од тип NonExistingItemException со порака Item with id [id] doesn't exist.
* **String getLog()** - враќа стринг со сите пораки запишани при архивирањето и отварањето архиви во посебен ред.

За секоја акција на архивирање во текст треба да се додаде следната порака **Item [id] archived at [date]**,
додека за секоја акција на отварање архива треба да се додаде Item **[id] opened at [date]**.
При отварање ако се работи за **LockedArhive** и датумот на отварање е пред датумот кога може да се отвори,
да се додаде порака **Item [id] cannot be opened before [date]**. Ако се работи за **SpecialArhive** и се обидиеме да ја
отвориме повеќе пати од дозволениот број (maxOpen) да се додаде порака **Item [id] cannot be opened more than [maxOpen] times**.

Read this: http://www.javapractices.com/topic/TopicAction.do?Id=31
