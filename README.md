# pdfDownloader

Basic web scrawler for downloading PDF files in Java.

## Objective

Used to download all PDF from http://repository.root-me.org/ which gathers a large amount of security resources.
It may not work for other websites for now.

My main objective was to get my hands dirty with the Jsoup library in Java.

The following command does same the job :
``wget --no-verbose --recursive --accept .pdf http://repository.root-me.org/ ``

## Installing pdfDownloader

Follow these steps to download and use pdfDownloader :
1. Downaload pdfDownloader.jar and save it to your favourite folder.
2. Start the program by typing java -jar pdfDownloader.jar url [path].

Without a given path, it will create a directory called "pdfDownloads" and download all PDF files there.
