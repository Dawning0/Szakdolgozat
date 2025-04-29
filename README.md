# Metronóm Alkalmazás

Ez a projekt egy metronóm alkalmazást valósít meg Android platformon Jetpack Compose és Kotlin felhasználásával. Az alkalmazás támogatja különböző típusú metronómok kezelését, vizuális megjelenítést, és hanggenerálást.

## Főbb Funkciók

1. **Hanggenerálás**:  
   A metronóm képes szinuszhullám-alapú hangok, illetve mintaalapú WAV fájlok lejátszására.
   
2. **Vizualizáció**:  
   A `VisualMetronome` komponens vizuális visszajelzést nyújt az ütemekről és alosztásokról.

3. **Tempó Beállítás**:  
   A felhasználók könnyedén módosíthatják a metronóm tempóját BPM-ben (`Beats Per Minute`) a csúszkák és gombok segítségével.

4. **Időmutató Beállítás**:  
   Az időmutató (ütemmutató) konfigurálható, beleértve a számlálót (numerator) és nevezőt (denominator).

5. **Típusok Kezelése**:  
   Támogatott metronóm típusok: 
   - `Sine Metronome` (szinuszhullám-alapú)
   - `Pattern Metronome` (mintaalapú)

6. **Tartós Frissítés**:  
   A `MetronomeManager` biztosítja az állapot frissítését és a folyamatok zökkenőmentes kezelését.

---

## Mappa Struktúra

- **`beater.metronome`**  
  A metronóm logika és vizuális megjelenítés kezelését tartalmazza.  
  - `MetronomeManager`: A metronóm működésének központi vezérlője.
  - `VisualMetronome`: Vizualizációs komponens az ütemek megjelenítéséhez.

- **`beater.metronome.types`**  
  A metronóm típusainak definiálása és megvalósítása (szinusz- és mintaalapú).  
  - `PatternMetronome`: Mintaalapú metronóm.
  - `SineMetronome`: Szinuszhullám-alapú metronóm.

- **`beater.ui.pages`**  
  A felhasználói felület elemeinek definiálása, beleértve a metronóm oldalt.  
  - `MetronomePage`: Az alkalmazás főképernyője.

---

## Használat

1. **Metronóm Elindítása**:  
   - Nyisd meg az alkalmazást a telefonodon vagy emulátoron.  
   - A főképernyőn kattints a **Start** gombra a metronóm indításához.  
   - A vizuális ütemjelző megjelenik, és a hanggenerálás elindul.

2. **Tempó Módosítása**:  
   - Használd a csúszkát, hogy finoman állítsd be a metronóm BPM-jét (`Beats Per Minute`).  
   - A **+10**, **+1**, **-10**, és **-1** gombok segítségével gyorsan növelheted vagy csökkentheted a tempót.

3. **Ütemmutató Konfiguráció**:  
   - Kattints az `Time Signature` (Ütemmutató) mezőre.  
   - Válaszd ki a kívánt számláló (`Numerator`) és nevező (`Subdiv`) értékeket az interaktív **picker** segítségével.

4. **Típus Váltása**:  
   - A metronóm típusát az alkalmazás beállításaiban konfigurálhatod:  
     - **Sine Metronome**: Szinuszhullám-alapú hanggenerálás.  
     - **Pattern Metronome**: Előre rögzített hangminták lejátszása.

5. **Metronóm Leállítása**:  
   - A metronóm leállításához kattints a **Stop** gombra.  
   - A vizuális és hangjelzés megszűnik, és az állapot alaphelyzetbe áll.

6. **További Funkciók**:  
   - Engedélyezheted vagy letilthatod az alosztásokat az `Subdivision` kapcsolóval.  
   - A hangerő beállításához használd a csúszkát a kívánt szint eléréséhez.

---

## További Fejlesztési Lehetőségek

1. **Időzítés Pontosítása**:
    - Hang jobb pontoítása.

2. **Animációk**:
    - Vizualizáció szebbé tétele további animációkkal.

3. **További Hangok**:
    - Új metronóm minták és frekvenciák hozzáadása.
