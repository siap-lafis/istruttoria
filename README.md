<h1>Consultazione dell'istruttoria della Domanda Unica 2015</h1>

Template realizzato con JHipster versione 3.4.2

L'ambiente target prevede Java 8 e JBoss EAP 6.4.5, i file di installazione application server e patch sono su SharePoint Almaviva. Per il deploy su JBoss compilare con opzione "-Pprod,no-liquibase,no-swagger" e seguire indicazioni su <a href='https://jhipster.github.io/production/'>Using JHipster in production</a>.

Il driver Oracle non è incluso nel repository: seguire le istruzioni di JHipster per il setup. Attualmente l'accesso al DBMS deve essere configurato in pom.xml ed in application-dev(prod).yml, ma questa soluzione deve essere rivista per usare un datasource JNDI.

<p>TO DO</p>
<ul>
<li>Vedi documento <a href='https://almavivaitaliaspa.sharepoint.com/progetti/APPAG-OPPAB/_layouts/15/guestaccess.aspx?guestaccesstoken=did3qqc72Qqr6xpQTl9F%2fcGdeAZ%2bKJjMFhu%2bsoS9psU%3d&docid=2_1c523b833e87f4e9d805435abc98ed956&rev=1'>Test funzionali di InfoTN</a></li>
<li>Navigazione:<ul>
<li> Soggetto <-> Domanda -> Superfici -> Superfici inverdimento -> Obblighi inverdimento</li>
<li> Domanda -> Pagamento -> Superfifice pagata</li>
<li> Domanda -> Pagamento -> Capo pagato</li>
<li> Domanda -> Pagamento -> Penalità</li></ul></li>
<li>Verificare gestione autenticazione ed autorizzazoni.</li>
<li>Le stampe non funzionano da Android, non ho fatto test da iOS.</li>
</ul>
