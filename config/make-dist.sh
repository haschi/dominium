#!/usr/bin/env bash

echo "Gepacktes Projektarchiv wird erzeugt."

mvn clean
tar -zcvf ../haushaltsbuch.tar.gz ./

echo "Fertig."
