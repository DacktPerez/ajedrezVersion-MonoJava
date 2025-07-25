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
│PantallaPresentacion │────│   MenuJuego     │────│ TableroAjedrez  │────│ValidadorMovimiento│
│     (main)          │    │                 │    │                 │    │                 │
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

### 1. PantallaPresentacion.java
**Propósito**: Punto de entrada principal de la aplicación (main)

```java
public class PantallaPresentacion extends JFrame {
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
3. **Capturas Aleatorias**
4. **Movimientos de Peones Aleatorios**
5. **Movimientos Generales Aleatorios**
6. **Fallback**: Primer movimiento válido

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
    // 1. Recopilar todas las capturas posibles
    // 2. Almacenar en ArrayList
    // 3. Seleccionar aleatoriamente con Math.random()
}
```

#### 3. Movimiento de Peones Inteligente
```java
private static int[] moverPeonAleatorio(String[][] piezas) {
    // 1. Buscar peones en TODO el tablero (no solo iniciales)
    // 2. Incluir movimientos de avance Y capturas diagonales
    // 3. Considerar movimiento doble desde posición inicial
    // 4. Selección aleatoria entre opciones válidas
}
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

## 📊 Manejo de Estados

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
    java.util.List<int[]> movimientos = new java.util.ArrayList<>();
    
    // Lógica de la estrategia
    for (int fila = 0; fila < 8; fila++) {
        for (int col = 0; col < 8; col++) {
            // Evaluar movimientos
            if (/* condición */) {
                movimientos.add(new int[]{fila, col, nuevaFila, nuevaCol});
            }
        }
    }
    
    // Selección aleatoria
    if (!movimientos.isEmpty()) {
        int indice = (int)(Math.random() * movimientos.size());
        return movimientos.get(indice);
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
// Para testing:
private String[][] crearTableroTest() {
    String[][] tablero = new String[8][8];
    // Configurar posiciones específicas para test
    return tablero;
}
```

### Optimizaciones

#### Performance
- Usar `StringBuilder` para concatenación de strings
- Cache de movimientos válidos
- Lazy loading de validaciones costosas

#### Memoria
- Reutilizar objetos `ArrayList`
- Implementar pooling de arrays temporales

## 📝 Convenciones de Código

### Nomenclatura
- **Clases**: PascalCase (`TableroAjedrez`)
- **Métodos**: camelCase (`obtenerMovimiento`)
- **Variables**: camelCase (`filaOrigen`)
- **Constantes**: UPPER_SNAKE_CASE (`MAX_FILAS`)

### Estructura de Métodos
```java
private tipo nombreMetodo(parametros) {
    // 1. Validaciones de entrada
    if (condicionInvalida) return valorDefault;
    
    // 2. Lógica principal
    tipo resultado = calcularResultado();
    
    // 3. Post-procesamiento
    procesarResultado(resultado);
    
    return resultado;
}
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

Esta documentación debe ser actualizada cada vez que se añadan nuevas funcionalidades al juego.
