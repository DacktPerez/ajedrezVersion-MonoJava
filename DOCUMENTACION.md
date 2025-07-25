# 📖 Documentación Técnica - Ajedrez Java

## 📋 Índice
1. [Arquitectura del Sistema](#arquitectura-del-sistema)
2. [Clases Principales](#clases-principales)
3. [Funcionalidades Implementadas](#funcionalidades-implementadas)
4. [Sistema de Bot](#sistema-de-bot)
5. [Validación de Movimientos](#validación-de-movimientos)
6. [Manejo de Estados](#manejo-de-estados)
7. [Guía de Desarrollo](#guía-de-desarrollo)

## 🏗️ Arquitectura del Sistema

### Diagrama de Componentes
```
┌─────────────────────┐    ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│Main                  │────│   MenuJuego     │────│ TableroAjedrez  │────│ValidadorMovimiento│
│                     │    │                 │    │                 │    │                 │
└─────────────────────┘    └─────────────────┘    └─────────────────┘    └─────────────────┘
                                                           │
                                                   ┌─────────────────┐
                                                   │    BotFacil     │
                                                   │                 │
                                                   └─────────────────┘
```

### Patrón de Diseño
- **MVC (Modelo-Vista-Controlador)**
  - **Modelo**: `piezas[][]` - Estado del tablero
  - **Vista**: Componentes Swing (JLabel, JPanel)
  - **Controlador**: Métodos de manejo de eventos en `TableroAjedrez`

## 🔧 Clases Principales

### 1. Main.java
**Propósito**: Punto de entrada principal de la aplicación (main)

```java
public class Main extends JFrame {
    // Métodos principales:
    - main()                  // Punto de entrada del programa
    - mostrarPresentacion()   // Pantalla de bienvenida
    - iniciarMenuJuego()      // Navegación al menú principal
}
```

**Responsabilidades**:
- Punto de entrada del programa
- Mostrar pantalla de bienvenida/logos
- Dirigir al usuario al menú principal

### 2. MenuJuego.java
**Propósito**: Menú principal del juego y selección de modo

```java
public class MenuJuego extends JFrame {
    // Métodos principales:
    - mostrarMenu()           // Interfaz principal
    - iniciarJuegoVsJugador() // Modo 1v1
    - iniciarJuegoVsBot()     // Modo PvE
}
```

**Responsabilidades**:
- Mostrar opciones de juego
- Crear instancia de `TableroAjedrez` según el modo
- Gestionar la navegación del menú

### 3. TableroAjedrez.java
**Propósito**: Motor principal del juego

```java
public class TableroAjedrez extends JFrame {
    // Atributos principales:
    private JLabel[][] celdas           // GUI del tablero
    private String[][] piezas           // Estado lógico
    private String turnoActual          // Control de turnos
    private boolean contraBot           // Modo de juego
    
    // Estados para enroque:
    private boolean reyBlancoMovido
    private boolean reyNegroMovido
    private boolean torreBlancaIzquierdaMovida
    private boolean torreBlancaDerechaMovida
    private boolean torreNegraIzquierdaMovida
    private boolean torreNegraDerechaMovida
}
```

**Métodos Clave**:
- `manejarClick()`: Gestiona interacción del usuario
- `realizarMovimientoBot()`: Ejecuta movimientos del bot
- `puedeHacerEnroque()`: Valida condiciones de enroque
- `realizarEnroque()`: Ejecuta el enroque
- `coronarPeon()`: Maneja la promoción de peones
- `estaEnJaque()`: Detecta situaciones de jaque
- `esJaqueMate()`: Verifica jaque mate

### 4. ValidadorMovimiento.java
**Propósito**: Validación de todos los movimientos de piezas

```java
public class ValidadorMovimiento {
    public static boolean esMovimientoValido(
        String[][] piezas, 
        String pieza, 
        int filaOrigen, int colOrigen, 
        int filaDestino, int colDestino
    )
}
```

**Validaciones por Pieza**:
- **Peón**: Avance, captura diagonal, movimiento doble inicial
- **Torre**: Líneas horizontales y verticales
- **Alfil**: Líneas diagonales
- **Caballo**: Movimiento en "L"
- **Reina**: Combinación torre + alfil
- **Rey**: Una casilla en cualquier dirección + enroque especial

### 5. BotFacil.java
**Propósito**: Inteligencia artificial para el oponente

```java
public class BotFacil {
    public static int[] obtenerMovimiento(String[][] piezas)
}
```

**Estrategia del Bot**:
1. **Escape de Jaque** (Prioridad Máxima)
2. **Distracción Aleatoria** (20% probabilidad)
3. **Capturas Aleatorias** (usando arrays fijos)
4. **Movimientos de Peones Aleatorios** (todo el tablero + capturas)
5. **Movimientos Generales Aleatorios** (con mezcla manual)
6. **Fallback**: Primer movimiento válido

### Arquitectura 

```java
int[][] movimientos = new int[maxMovimientos][4];
int contador = 0;
movimientos[contador][0] = fila; // Asignación directa
contador++;
return new int[]{movimientos[indice][0], movimientos[indice][1], /* ... */};
```

#### Ventajas de la Implementación:
- ✅ **Mejor rendimiento**: Arrays son más rápidos que listas dinámicas
- ✅ **Memoria predecible**: Tamaño fijo conocido en tiempo de compilación
- ✅ **Control total**: Manejo manual de índices y límites

## 🎮 Funcionalidades Implementadas

### 1. 🏰 Sistema de Enroque

#### Validaciones Implementadas:
- ✅ Rey y torre no se han movido
- ✅ No hay piezas entre rey y torre
- ✅ Rey no está en jaque
- ✅ Rey no pasa por casillas atacadas
- ✅ Posiciones iniciales correctas

#### Código Clave:
```java
private boolean puedeHacerEnroque(String color, boolean esEnroqueCorto) {
    // 1. Verificar estados de movimiento
    // 2. Verificar posiciones iniciales
    // 3. Verificar camino libre
    // 4. Verificar que rey no esté en jaque
    // 5. Simular movimiento para verificar casillas atacadas
}
```

### 2. 👑 Sistema de Coronación

#### Características:
- **Jugador Humano**: Interfaz de selección interactiva
- **Bot**: Promoción automática a reina
- **Opciones**: Reina, Torre, Alfil, Caballo

#### Código Clave:
```java
private void coronarPeon(int fila, int columna, String color) {
    String[] opciones = {"Reina", "Torre", "Alfil", "Caballo"};
    int seleccion = JOptionPane.showOptionDialog(/* ... */);
    // Crear nueva pieza según selección
}
```

### 3. ⚡ Sistema de Jaque y Jaque Mate

#### Detección de Jaque:
```java
private boolean estaEnJaque(String[][] tablero, String colorDelRey) {
    // 1. Encontrar posición del rey
    // 2. Verificar si alguna pieza enemiga puede atacarlo
    // 3. Usar ValidadorMovimiento para verificar ataques
}
```

#### Detección de Jaque Mate:
```java
private boolean esJaqueMate(String color) {
    // 1. Verificar que esté en jaque
    // 2. Probar todos los movimientos posibles
    // 3. Simular cada movimiento
    // 4. Verificar si alguno escapa del jaque
}
```

## 🤖 Sistema de Bot Avanzado

### Arquitectura de Decisión

```
┌─────────────────┐
│ ¿Rey en Jaque?  │ ──Yes──→ buscarEscapeDeJaque()
└─────────────────┘
         │ No
         ▼
┌─────────────────┐
│ ¿Distracción?   │ ──20%──→ buscarMovimientoAleatorio()
│    (20% prob)   │
└─────────────────┘
         │ 80%
         ▼
┌─────────────────┐
│ Buscar Capturas │ ──→ buscarCapturaAleatoria()
└─────────────────┘
         │
         ▼
┌─────────────────┐
│ Mover Peones    │ ──→ moverPeonAleatorio()
└─────────────────┘
         │
         ▼
┌─────────────────┐
│ Movimiento      │ ──→ buscarMovimientoAleatorio()
│ General         │
└─────────────────┘
```

### Algoritmos de Movimiento

#### 1. Escape de Jaque
```java
private static int[] buscarEscapeDeJaque(String[][] piezas) {
    // 1. Intentar mover el rey a casilla segura
    // 2. Si no es posible, intentar bloquear/capturar atacante
    // 3. Simular cada movimiento para verificar efectividad
}
```

#### 2. Capturas Aleatorias
```java
private static int[] buscarCapturaAleatoria(String[][] piezas) {
    // 1. Crear array bidimensional para almacenar movimientos (64x4)
    int[][] capturasPosibles = new int[64][4];
    int contador = 0;
    
    // 2. Recopilar todas las capturas posibles
    for (cada pieza negra en el tablero) {
        for (cada posición destino) {
            if (hay pieza blanca && movimiento válido) {
                // Almacenar: [filaOrigen, colOrigen, filaDestino, colDestino]
                capturasPosibles[contador] = movimiento;
                contador++;
            }
        }
    }
    
    // 3. Seleccionar aleatoriamente usando Math.random() y contador
    return capturasPosibles[random * contador];
}
```

#### 3. Movimiento de Peones
```java
private static int[] moverPeonAleatorio(String[][] piezas) {
    // 1. Array para movimientos de peones (32x4 - suficiente para todos los peones)
    int[][] movimientosPeones = new int[32][4];
    int contador = 0;
    
    // 2. Buscar peones en TODO el tablero (no solo posición inicial)
    for (int fila = 0; fila < 8; fila++) {
        for (cada peón negro encontrado) {
            // a) Movimiento adelante (1 casilla)
            // b) Movimiento doble desde posición inicial (fila == 1)
            // c) Capturas diagonales (izquierda y derecha)
            if (movimiento válido) {
                movimientosPeones[contador] = movimiento;
                contador++;
            }
        }
    }
    
    // 3. Selección aleatoria entre todos los movimientos válidos
    return movimiento aleatorio;
}
```

#### 4. Movimientos Generales Aleatorios
```java
private static int[] buscarMovimientoAleatorio(String[][] piezas) {
    // 1. Array grande para múltiples movimientos (200x4)
    int[][] movimientosPosibles = new int[200][4];
    int contador = 0;
    
    // 2. Mezcla manual de tipos de piezas (sin Collections.shuffle)
    String[] tiposPiezas = {"peon", "caballo", "alfil", "torre", "reina"};
    for (int i = 0; i < tiposPiezas.length; i++) {
        int j = (int)(Math.random() * tiposPiezas.length);
        // Intercambiar elementos manualmente
        swap(tiposPiezas[i], tiposPiezas[j]);
    }
    
    // 3. Buscar movimientos con rango variable
    for (cada tipo de pieza en orden aleatorio) {
        int rangoMax = (Math.random() < 0.7) ? 2 : 4; // Movimientos cortos/largos
        
        // 4. Comportamientos especiales:
        if (Math.random() < 0.3) return movimiento_impulsivo; // 30% impulsivo
        if (Math.random() < 0.4) break; // 40% no seguir buscando
    }
    
    return movimiento_aleatorio;
}
```

## 🧠 Inteligencia del Bot Mejorada

### Comportamientos Avanzados Implementados

#### 1. **Movimiento de Peones Inteligente**
```java
// El bot ahora considera:
- Peones en TODO el tablero (no solo posición inicial)
- Capturas diagonales automáticas
- Movimiento doble desde fila inicial
- Selección aleatoria entre todas las opciones válidas
```

#### 2. **Sistema de Aleatoriedad Sofisticado**
```java
// Múltiples niveles de randomización:
- 20% probabilidad de "distracción" (ignora capturas)
- 30% probabilidad de movimiento "impulsivo" 
- 40% probabilidad de no seguir buscando más opciones
- 70% movimientos cortos vs 30% movimientos largos
- Mezcla aleatoria del orden de evaluación de piezas
```

#### 3. **Gestión de Jaque Robusta**
```java
private static int[] buscarEscapeDeJaque(String[][] piezas) {
    // 1. Prioridad: Mover el rey a casilla segura
    // 2. Alternativa: Bloquear/capturar atacante
    // 3. Simulación completa para verificar efectividad
    // 4. Solo retorna movimientos que realmente escapan del jaque
}
```

#### 4. **Optimización de Estructuras de Datos**
```java
// Tamaños optimizados según análisis real:
int[][] capturasPosibles = new int[64][4];   // Máximo teórico de capturas
int[][] movimientosPeones = new int[32][4];  // 8 peones × 4 movimientos máx
int[][] movimientosPosibles = new int[200][4]; // Buffer amplio para seguridad
```

## 🔍 Sistema de Validación

### Jerarquía de Validación

```
esMovimientoValido()
├── Validaciones Generales
│   ├── Límites del tablero
│   ├── Pieza origen válida
│   └── No capturar pieza propia
├── Validaciones por Tipo
│   ├── validarMovimientoPeon()
│   ├── validarMovimientoTorre()
│   ├── validarMovimientoAlfil()
│   ├── validarMovimientoCaballo()
│   ├── validarMovimientoReina()
│   └── validarMovimientoRey()
└── Validaciones de Camino
    └── esCaminoLibre()
```

### Ejemplo: Validación de Peón
```java
private static boolean validarMovimientoPeon(String[][] piezas, String pieza, 
                                           int fo, int co, int fd, int cd) {
    boolean esBlanco = pieza.contains("blanco");
    int direccion = esBlanco ? -1 : 1;
    int df = fd - fo;
    int dc = cd - co;
    
    // Movimiento hacia adelante
    if (dc == 0) {
        if (df == direccion && piezas[fd][cd] == null) return true;
        if (df == 2 * direccion && /* posición inicial */ && 
            piezas[fd][cd] == null && piezas[fo + direccion][co] == null) {
            return true;
        }
    }
    
    // Captura diagonal
    if (Math.abs(dc) == 1 && df == direccion) {
        String piezaDestino = piezas[fd][cd];
        return piezaDestino != null && !mismoBando(pieza, piezaDestino);
    }
    
    return false;
}
```

## 📊 Manejo de Estados del Bot

### Estructuras de Datos Optimizadas
```java
// Usando arrays simples
private static int[] buscarCapturaAleatoria(String[][] piezas) {
    int[][] capturasPosibles = new int[64][4]; // Array bidimensional
    int contador = 0; // Control manual de elementos
    
    // Llenado manual sin .add()
    capturasPosibles[contador][0] = filaOrigen;
    capturasPosibles[contador][1] = colOrigen;
    capturasPosibles[contador][2] = filaDestino;
    capturasPosibles[contador][3] = colDestino;
    contador++;
    
    // Selección sin .get() - acceso directo
    int indiceAleatorio = (int)(Math.random() * contador);
    return new int[]{capturasPosibles[indiceAleatorio][0], /* ... */};
}
```

### Técnicas de Optimización Implementadas
- **Arrays Fijos**: Tamaños predefinidos para evitar redimensionamiento
- **Contadores Manuales**: En lugar de `.size()` de listas dinámicas
- **Acceso Directo**: Sin métodos `.get()` o `.add()`
- **Mezclas Manuales**: Algoritmo Fisher-Yates simplificado sin Collections

### Gestión de Memoria Eficiente
```java
// Tamaños optimizados según uso real:
int[][] capturasPosibles = new int[64][4];   // Máximo capturas posibles
int[][] movimientosPeones = new int[32][4];  // Suficiente para 8 peones
int[][] movimientosPosibles = new int[200][4]; // Movimientos generales

// Control de desbordamiento
if (contador < arraySize) {
    // Almacenar movimiento
    contador++;
}
```

### Estados del Juego
```java
// Estados de piezas (para enroque)
private boolean reyBlancoMovido = false;
private boolean reyNegroMovido = false;
private boolean torreBlancaIzquierdaMovida = false;
// ... otras torres

// Estado del turno
private String turnoActual = "blanco";

// Configuración del juego
private boolean contraBot = false;

// Selección del usuario
private int filaOrigen = -1;
private int colOrigen = -1;
private JLabel celdaSeleccionada = null;
```

### Actualización de Estados
```java
private void actualizarEstadoPiezasMovidas(int fila, int columna, String pieza) {
    if (pieza.contains("rey")) {
        if (pieza.contains("blanco")) reyBlancoMovido = true;
        else reyNegroMovido = true;
    } else if (pieza.contains("torre")) {
        // Identificar qué torre específica se movió
        if (pieza.contains("blanco") && fila == 7) {
            if (columna == 0) torreBlancaIzquierdaMovida = true;
            else if (columna == 7) torreBlancaDerechaMovida = true;
        }
        // Similar para torres negras...
    }
}
```

## 🛠️ Guía de Desarrollo

### Añadir Nueva Funcionalidad

#### 1. Nueva Pieza
```java
// En ValidadorMovimiento.java
private static boolean validarMovimientoNuevaPieza(/*parámetros*/) {
    // Lógica de movimiento
    return true;
}

// En esMovimientoValido()
if (tiposPieza.contains("nuevapieza")) {
    return validarMovimientoNuevaPieza(/*parámetros*/);
}
```

#### 2. Nueva Regla
```java
// En TableroAjedrez.java
private boolean puedeAplicarNuevaRegla(/*parámetros*/) {
    // Validaciones específicas
    return true;
}

// En manejarClick()
if (esMovimientoNuevaRegla(/*parámetros*/)) {
    if (puedeAplicarNuevaRegla(/*parámetros*/)) {
        realizarNuevaRegla(/*parámetros*/);
        return;
    }
}
```

### Mejoras del Bot

#### Añadir Nuevo Comportamiento
```java
// En BotFacil.java
private static int[] nuevaEstrategia(String[][] piezas) {
    int[][] movimientos = new int[100][4]; // Array fijo en 
    int contador = 0;
    
    // Lógica de la estrategia
    for (int fila = 0; fila < 8; fila++) {
        for (int col = 0; col < 8; col++) {
            // Evaluar movimientos
            if (/* condición */ && contador < 100) {
                movimientos[contador][0] = fila;
                movimientos[contador][1] = col;
                movimientos[contador][2] = nuevaFila;
                movimientos[contador][3] = nuevaCol;
                contador++;
            }
        }
    }
    
    // Selección aleatoria 
    if (contador > 0) {
        int indice = (int)(Math.random() * contador);
        return new int[]{movimientos[indice][0], movimientos[indice][1],
                        movimientos[indice][2], movimientos[indice][3]};
    }
    
    return null;
}

// En obtenerMovimiento()
int[] nuevoMov = nuevaEstrategia(piezas);
if (nuevoMov != null) return nuevoMov;
```

### Debugging y Testing

#### Activar Mensajes de Debug
```java
// Añadir en métodos clave:
System.out.println("DEBUG: " + mensaje);

// Ejemplo en enroque:
if (reyBlancoMovido) {
    System.out.println("DEBUG: Rey blanco ya se movió");
    return false;
}
```

#### Simular Estados
```java
// Para testing del bot:
private static String[][] crearTableroTest() {
    String[][] tablero = new String[8][8];
    // Configurar posiciones específicas para test
    tablero[0][4] = "resources/rey_negro.png";
    tablero[7][4] = "resources/rey_blanco.png";
    // ... más configuraciones
    return tablero;
}

// Test de arrays
private static void testArrayBot() {
    String[][] tableroTest = crearTableroTest();
    int[] movimiento = BotFacil.obtenerMovimiento(tableroTest);
    
    System.out.println("Movimiento obtenido: [" + 
        movimiento[0] + "," + movimiento[1] + "," + 
        movimiento[2] + "," + movimiento[3] + "]");
}

// Verificar contadores en métodos:
private static int[] debugMovimientos(String[][] piezas) {
    int[][] movimientos = new int[200][4];
    int contador = 0;
    
    // ... lógica del bot ...
    
    System.out.println("DEBUG: Se encontraron " + contador + " movimientos");
    if (contador == 0) {
        System.out.println("DEBUG: ¡No hay movimientos disponibles!");
    }
    
    return (contador > 0) ? seleccionarMovimiento(movimientos, contador) : null;
}
```

### Optimizaciones

#### Performance
- Usar arrays fijos
- Acceso directo por índice en lugar de métodos `.get()`
- Contadores manuales en lugar de `.size()`
- Mezclas de arrays sin dependencias de `Collections.shuffle()`

#### Memoria
- Arrays predimensionados según uso real esperado
- Sin crecimiento dinámico de estructuras de datos
- Reutilización de arrays locales en métodos
- Control manual de límites para evitar desbordamiento

#### Dependencias Minimizadas
- Sin importaciones de `java.util.*`
- Sin uso de clases de colecciones
- Algoritmos implementados manualmente
- Código autocontenido sin dependencias externas

## 📝 Convenciones de Código

### Nomenclatura
- **Clases**: PascalCase (`TableroAjedrez`)
- **Métodos**: camelCase (`obtenerMovimiento`)
- **Variables**: camelCase (`filaOrigen`)
- **Constantes**: UPPER_SNAKE_CASE (`MAX_FILAS`)

### Estructura de Métodos
```java
private static int[] nombreMetodo(String[][] piezas) {
    // 1. Declarar array fijo y contador
    int[][] resultados = new int[TAMAÑO_MAXIMO][4];
    int contador = 0;
    
    // 2. Lógica principal con validaciones
    for (bucles anidados) {
        if (condicionValida && contador < TAMAÑO_MAXIMO) {
            resultados[contador][0] = valor1;
            resultados[contador][1] = valor2;
            resultados[contador][2] = valor3;
            resultados[contador][3] = valor4;
            contador++;
        }
    }
    
    // 3. Selección aleatoria o retorno
    if (contador > 0) {
        int indice = (int)(Math.random() * contador);
        return new int[]{resultados[indice][0], resultados[indice][1],
                        resultados[indice][2], resultados[indice][3]};
    }
    
    return null;
}
```

### Mejores Prácticas para Arrays
```java
// ✅ Correcto: Tamaños apropiados
int[][] capturas = new int[64][4];    // Máximo teórico
int[][] peones = new int[32][4];      // 8 peones × 4 mov. máx
int[][] generales = new int[200][4];  // Buffer amplio

// ✅ Control de desbordamiento
if (contador < TAMAÑO_ARRAY) {
    array[contador] = valor;
    contador++;
}

// ✅ Verificación antes de acceso
if (contador > 0) {
    int indice = (int)(Math.random() * contador);
    return array[indice];
}

// ❌ Evitar: Tamaños insuficientes o acceso sin verificar
int[][] pequeño = new int[5][4];     // Muy pequeño
return array[indice];                // Sin verificar límites
```

### Comentarios
```java
// Comentario de línea para explicaciones breves

/**
 * Comentario de bloque para métodos públicos
 * @param parametro Descripción del parámetro
 * @return Descripción del valor de retorno
 */

/* Comentario multilínea para 
   explicaciones más largas */
```

---
