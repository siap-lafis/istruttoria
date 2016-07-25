Consultazione dell'istruttoria della Domanda Unica 2015

Template realizzato con JHipster versione 3.4.2

Il driver Oracle non è incluso nel repository: seguire le istruzioni di JHipster per il setup. L'accesso al DBMS deve essere configurato in pom.xml ed in application-dev.yml.

<p>TO DO</p>
<ul>
<li>Stampa della scheda controlli: prospetto jasperreports e recupero dati.</li>
<li>Paginazione (fatto su superfici)</li>
<li>Navigazione: Soggetto <-> Domanda -> Superfici<ul>
<li>-> Superfici inverdimento -> Obblighi inverdimento</li>
<li>-> Pagamento -> Penalità</li>
<li>-> Capo pagato</ul></li>
<li>Esecuzione su Tomcat esterno: impostare datasource nel file di configurazione "application-prod.yml", modificare il profilo "prod" in pom.xml inserendo "prod,no-liquibase,no-swagger", quindi compilare con opzione "-Pprod package".</li>
<li>Integrazione autenticazione: da verificare ma probabile che la security implementata da JHipster debba essere rimossa, verificare se possibile sostituire con un filtro servlet che rimandi ad SSO Abaco.</li>
<li>Personalizzazione BZ, Internazionalizzazione, Label colonne e pulsanti.</li>
</ul>
