Integrationstests mit Deltaspike
================================

1. Tests selbst müssen CDI Beans sein: In der Konfigurationsdatei apache-deltaspike.properties
muss das Flag `deltaspike.testcontrol.use_test_class_as_cdi_bean` den Wert `true` besitzen.
2. Die Tests müssen mit `@RunWith(CdiTestRunner.class)` annotiert sein.
3. Die Test-Bean muss den Transaktions-Kontext starten. Dazu muss der Test mit
`@Transactional` annotiert sein.
4. Test-Klassen müssen mit `@RequestScoped` annotiert werden, damit genau eine Instanz
pro Test-Methode erzeugt wird.