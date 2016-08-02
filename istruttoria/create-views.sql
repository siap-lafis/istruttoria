Select view_Name From User_views Where View_Name In (
'SOGGETTO', 'DOMANDA', 'SUPERFICIE', 'SUPERFICIE_INVERDIMENTO',
'ELENCO_PAGAMENTO', 'OBBLIGO_INVERDIMENTO', 'PAGAMENTO',
'PENALITA', 'SUPERFICIE_PAGATA', 'CAPO_PAGATO');

/*
 * DDL CREAZIONE VISTE
 */
--versione BZ
Create or replace View Superficie As (Select Tdu_Gis_Id As Id, Cod_Nazionale, Foglio, Cod_Intervento,
Cod_Coltura, Supe_Util As Supe_Dich, Supe_Ammi, Supe_Ammi_Netta, Id_Domanda As Domanda_Id
From Temp_Doma_Utilizzo_Gis Where Id_Domanda In (Select Id_Domanda From Domanda));

--versione TN
Create Or Replace View Superficie As 
(Select rownum as id, Cod_Nazionale, Foglio, Codice_Intervento As Cod_Intervento, 
Cod_Utilizzo||'-'||Codice_Prodotto||'-'||Codice_Varieta As Cod_Coltura, 
Sum(Superficie_Utilizz) As Supe_Dich, Null As Supe_Ammi, Sum(Fg_Supe_Ammi) As Supe_Ammi_Netta, 
Id_Domanda As Domanda_Id From T_Du_Utilizzo_Gis 
Where Id_Domanda In (Select Id_Domanda From Domanda)
Group By rownum, Cod_Nazionale, Foglio, Codice_Intervento, Cod_Utilizzo, Codice_Prodotto, Codice_Varieta, Id_Domanda);

Create View Capo_Pagato As (Select rownum As Id, Z.Marca_Capo, Z.Ammissibile, 
Z.N_Uba As Num_Uba, Z.Mancanza_Analisi_Latte, Z.Medie_Latte_Soma, Z.Medie_Latte_Germ, 
Z.Medie_Latte_Prot, C.Codi_Asll As Cod_Asl, C.Flag_Sess, C.Data_Nasc, C.Codi_Razz, 
C.Data_Iniz_Dete, C.Data_Fine_Dete, Null As Pagamento_Id
From Vpaga_Cont_Zoot Z, Aduxcapi_Tab C Where Z.Marca_Capo = C.Codi_Marc);

-- Drop Table Penalita; 
Create Or Replace View Penalita As (Select Rownum As Id, p.Deco_Tipo_Pena, p.Qnta_Pena, p.Impo_Pena, 
P.Unit_Misu, To_Number(P.Id_Decr||P.Id_Atto_Ammi||P.Id_Inte||M.Fasc_Modu) As Pagamento_Id From Aduxpena_Tab P, Aduximut_Tab M
Where p.id_decr = m.id_decr and p.id_atto_ammi = m.id_atto_ammi and p.id_inte = m.id_inte);

Create or replace View Superficie_Pagata As 
(Select Rownum As Id, Supe_Dich, Supe_Ammi, Supe_Refr, Supe_Dete, Supe_Nsan, 
Supe_Acce, Notito_Dich As Num_Tito_Dich, Notito_Dete As Num_Tito_Dete, 
to_number(id_decr||Id_Atto_Ammi||id_inte) As Pagamento_Id
From Aduxespa_Tab);

Create Or Replace View Pagamento As 
Select to_number(id_decr||Id_Atto_Ammi||id_inte||fasc_modu) As Id, Id_Atto_Ammi, Decode(Id_Inte, 407, 'BPS1', 'GREENING') As Cod_Intervento, 
Qnta_Dich, Qnta_Amme, Qnta_Liqu, Impo_Dich, Impo_Amme, Impo_Liqu, Stat_Liqu_Doma As Stat_Liqu, 
Unit_Misu, Codi_Nume_Capi_Spes, To_Char(Data_Elab, 'dd/MM/yyyy') As Data_Elab, 
Codi_Esit_Gcol As Codi_Esi_Gcol, Perc_Sanz_Gcol, Perc_Sanz_Azie, Valo_Medi_Tito, 
Impo_Trat_Modu, Fasc_Modu, Impo_Trat_Fina, To_Number(Id_Decr||Id_Atto_Ammi) As Elenco_Pagamento_Id 
From Aduximut_Tab Order By 1;

-- Drop Table Obbligo_Inverdimento;
Create or replace View Obbligo_Inverdimento As (Select Rownum As Id,
Qnta_Supe_Semi As Supe_Semi, Qnta_Supe_Prat_Perm As Supe_Prat_Perm, 
Qnta_Supe_Fora As Supe_Fora, Deco_Eson_Dive, Deco_Eson_Efa, 
Flag_Risp_Colt, Flag_Risp_Colt_Rima, Flag_Risp_Li75 As Flag_Risp_75_P, 
Flag_Risp_Li95 As Flag_Risp_95_P,
Flag_Risp_Efa, To_Number(Id_Decr||Id_Atto_Ammi) as superfici_inverdimento_id From Aduxinve1_Tab);

-- Drop Table Superficie_Inverdimento;
Create or replace View Superficie_Inverdimento As (Select To_Number(Id_Decr||Id_Atto_Ammi) As Id, Supe_Semi, 
Supe_Prim As Supe_Prim_Colt, Supe_Seco As Supe_Seco_Colt, Supe_Altr As Supe_Altr_Colt, 
Supe_Prim_Max, Supe_Seco_Max, Supe_Prim_Diff1 As Supe_Prim_Diff_1, 
Supe_Prim_Diff2 As Supe_Prim_Diff_2, Tasso_Diff_Prim, Supe_Prim_Ridu, 
supe_seco_diff1 as SUPE_SECO_DIFF_1, supe_seco_diff2 as SUPE_SECO_DIFF_2,
Tasso_Diff_Seco, Supe_Seco_Ridu, Tota_Tasso_Diff, Supe_Ridu_Dive, Supe_Efa, 
Supe_Efa_Obbl, Supe_Efa_Diff, Tasso_Diff_Efa, Supe_Ridu_Efa, Tota_Ridu, Supe_Paga_Semi,
Supe_Prat_Sens, Supe_Prat_Nsen As Supe_Prat_Nsens, Supe_Perm, Supe_Inve, 
Id_Atto_Ammi As Domanda_Id From Aduxesin1_Tab where id_atto_ammi in (select id from domanda));

Create Or Replace View Domanda As 
Select Id_Domanda As Id, Id_Domanda, 
to_char(Dt_Inizio, 'dd/MM/yyyy') As Data_Pres, to_char(Dt_Insert, 'dd/MM/yyyy') As Data_Inse, 'DU' As Cod_Settore, 
2015 As Anno, Id_Soggetto As Soggetto_Id From Tdom_Domanda
Where Id_Modulo In (Select Id_Modulo From Tdom_Modulo Where Sco_Settore = 'PI2014' 
And Anno = 2015) And Sco_Stato = '000066' And Sysdate Between Dt_Inizio And Dt_Fine
And Sysdate Between Dt_Insert And Dt_Delete And Id_Domanda In (
Select Id_Atto_Ammi From Aduximut_Tab);

Create Or Replace View Elenco_Pagamento As
Select Distinct To_Number(Id_Decr||Id_Atto_Ammi) As Id, Id_Decr, To_Char(Data_Elab, 'dd/MM/yyyy') As Data_Decr, 
Id_Atto_Ammi As Domanda_Id From Aduximut_Tab Order By 1;


CREATE OR REPLACE FORCE VIEW FASCICOLO.SOGGETTO
(
   ID,
   CUAA,
   DENOMINAZIONE,
   NOME,
   COGNOME,
   PARTITAIVA
)
AS
   SELECT DISTINCT A.Cod_Soggetto AS Id,
                   A.Cod_Fiscale AS Cuaa,
                   TRIM (A.Denominazione) AS Denominazione,
                   nome AS Nome,
                   cognome AS Cognome,
                   part_iva AS PartitaIVA
     FROM Anag_Soggetti A, Tdom_Domanda D, Aduximut_Tab X
    WHERE     A.Cod_Soggetto = D.Id_Soggetto
          AND D.Id_Domanda = X.Id_Atto_Ammi        
          AND a.data_fine_val > SYSDATE;
