# Leksika
Word search game in Latvian for Android OS

##Features
This fork is different from Healeys and Serwylos versions as the classes related to dictionary build and search are replaced.
Original dictionary build and search supported only 26 letters. Many changes were needed to  support more than 32 characters so eventually
I rewrote original Trie and dictionary related classes to use DAWG (see Credits). In theory
(only Latvian characters tested) the new dictionary supports all UTF-8 letters
and still is reasonably small and fast (1,4 mil Latvian words take 840K, read dictionary and solve 5x5 board takes less than 100ms)

##Reporting Issues
Please report any issues or suggest features on the [issue tracker](https://github.com/gatis/leksika/issues)  

## Credits
[Johnny Healey:](https://code.google.com/archive/p/lexic/) Creator of ofiginal Lexic for Android and releasing under GPL v3  
[Peter Serwylo:](https://github.com/lexica/lexica) Tweaks and clean-ups of the original Lexic  
[Dicollecte(lv) open source language resources:](http://dict.dv.lv/home.php?prj=lv) I used their Hunspell (LV) dictionary to obtain list of valid latvian words  
[Susumu Yata:](https://github.com/stil/dawgdic) Library for building and accessing dictionaries implemented with directed acyclic word graphs (DAWG)  
[Mangesh Hambarde:](https://github.com/stil/dawgdic) Java interface for accessing directed acyclic word graphs (DAWG)  
[Code, ideas:](http://stackoverflow.com/questions/746082/how-to-find-list-of-possible-words-from-a-letter-matrix-boggle-solver) and [resources for solving boggle boards:](http://www.gtoal.com/wordgames/boggle.html)  
[Andrejs Spektors, Mākslīgā intelekta laboratorija - tezaurs.lv](http://www.tezaurs.lv/)

## Special thanks
Maira - for beta testing, feedback and food

## Notes
You can download the App in [Google Play store](https://code.google.com/archive/p/lexic/):  https://play.google.com/store/apps/details?id=com.gatis.leksika
http://www.tezaurs.lv/ is not related with this app in any way. The links to http://www.tezaurs.lv/ are added just to enable look-up of meaning of particular word. Sometimes links work (word is in their dicionary) and sometimes they do not - look also for similar words as they may be inflected. Source of latvian words dictionary is Dicollecte(lv) open source language resource ('openoffice spellchecker' in laymans terms). Spellchecker dictionary may not be the best source for words but was the most complete i could find in the wild. The compressed dictionary contains 1459243 words and word-forms at this point (all presumably known Latvian words with length from 2 to 12 characters).
Have fun!
