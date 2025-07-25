# 🏆 Ajedrez Java - Versión Completa

Un juego de ajedrez completo desarrollado en Java con interfaz gráfica Swing que incluye todas las reglas oficiales del ajedrez y un bot inteligente para jugar en solitario.

## 🎮 Características Principales

### ✅ Funcionalidades Implementadas

- **🎯 Juego Completo de Ajedrez**: Todas las reglas oficiales implementadas
- **👥 Dos Modos de Juego**: 
  - Jugador vs Jugador (1v1)
  - Jugador vs Bot (PvE)
- **🏰 Enroque**: Tanto corto como largo, con validación completa
- **👑 Coronación de Peones**: Interfaz interactiva para elegir la pieza
- **🤖 Bot**: Nivel principiante
- **⚡ Detección de Jaque y Jaque Mate**: Sistema completo de verificación
- **🎨 Interfaz Gráfica Intuitiva**: Tablero visual con arrastrar y soltar
- **📋 Validación de Movimientos**: Sistema robusto de validación

### 🧩 Piezas y Movimientos

- ♟️ **Peones**: Movimiento adelante, captura diagonal, movimiento doble inicial
- 🏰 **Torres**: Movimiento horizontal y vertical
- 🐎 **Caballos**: Movimiento en "L"
- 🔷 **Alfiles**: Movimiento diagonal
- 👸 **Reina**: Combinación de torre y alfil
- 👑 **Rey**: Movimiento de una casilla en cualquier dirección + enroque

## 🚀 Instalación y Ejecución

### Requisitos
- Java 8 o superior
- IDE compatible con Java (recomendado: IntelliJ IDEA, Eclipse, VS Code)

### Pasos para ejecutar:

1. **Clonar o descargar el proyecto**
```bash
git clone [URL_DEL_REPOSITORIO]
cd ajedrezVersion-MonoJava
```

2. **Compilar el proyecto**
```bash
cd AjedrezJava/src
javac *.java
```

3. **Ejecutar el juego**
```bash
java Main
```

## 🎮 Cómo Jugar

### Inicio del Juego
1. Ejecuta `Main.java`
2. Selecciona el modo de juego:
   - **"Jugador vs Jugador"**: Para jugar con otra persona
   - **"Jugador vs Bot"**: Para jugar contra la computadora

### Controles
- **Seleccionar pieza**: Haz clic en la pieza que quieres mover
- **Mover pieza**: Haz clic en la casilla de destino
- **Cancelar selección**: Haz clic en la misma pieza seleccionada
- **Enroque**: Mueve el rey 2 casillas hacia la torre deseada
- **Coronación**: Al llegar un peón al final, aparecerá un menú de selección

### Reglas Especiales

#### 🏰 Enroque
- **Condiciones**: Rey y torre no se han movido, no hay piezas entre ellos, el rey no está en jaque
- **Enroque Corto**: Rey se mueve 2 casillas hacia la derecha
- **Enroque Largo**: Rey se mueve 2 casillas hacia la izquierda

#### 👑 Coronación de Peones
- Cuando un peón llega al final del tablero, puedes elegir entre:
  - Reina (más poderosa)
  - Torre
  - Alfil
  - Caballo

## 🤖 Sistema de Bot

### Características del Bot
- **Nivel Principiante**: Diseñado para jugadores que están aprendiendo
- **Comportamiento Realista**: Comete errores típicos de principiantes
- **Aleatoriedad**: Movimientos variados para mayor entretenimiento
- **Gestión de Jaque**: Sabe escapar cuando el rey está en peligro

### Estrategias del Bot
1. **Prioridad en Jaque**: Si está en jaque, busca escapar inmediatamente
2. **Capturas Aleatorias**: Busca capturas pero no siempre las mejores
3. **Movimiento de Peones**: Avanza peones de forma variada
4. **Movimientos Generales**: Mueve piezas de forma aleatoria pero válida
5. **Distracción**: 20% de probabilidad de hacer movimientos inesperados

## 📁 Estructura del Proyecto

```
AjedrezJava/
├── src/
│   ├── MenuJuego.java          # Menú del juego
│   ├── TableroAjedrez.java         # Lógica principal del tablero
│   ├── ValidadorMovimiento.java    # Validación de movimientos
│   ├── BotFacil.java              # Inteligencia artificial
│   └── Main.java   # Pantalla de bienvenida
├── resources/                      # Imágenes de las piezas
│   ├── rey_blanco.png
│   ├── rey_negro.png
│   ├── reina_blanco.png
│   └── ... (todas las piezas)
└── AjedrezJava.iml                # Configuración del proyecto
```

## 🔧 Características Técnicas

### Arquitectura
- **Patrón MVC**: Separación clara entre lógica y presentación
- **Programación Orientada a Objetos**: Clases bien definidas y encapsuladas
- **Swing GUI**: Interfaz gráfica responsiva y atractiva

## 👨‍💻 Desarrollo

### Estructura de Clases

- **`Main`**: Punto de entrada y selección de modo
- **`TableroAjedrez`**: Motor principal del juego
- **`ValidadorMovimiento`**: Lógica de validación de movimientos
- **`BotFacil`**: Inteligencia artificial del oponente
- **`MenuJuego`**: Interfaz de bienvenida

### Tecnologías Utilizadas
- Java SE 8+
- Swing (GUI)
- AWT (Manejo de eventos)

## 🐛 Solución de Problemas

### Problemas Comunes

1. **Imágenes no se cargan**
   - Verificar que la carpeta `resources/` esté en la ubicación correcta
   - Comprobar que todas las imágenes estén presentes

2. **Enroque no funciona**
   - Verificar que ni el rey ni la torre se hayan movido
   - Asegurarse de que no hay piezas entre ellos

3. **Error de compilación**
   - Verificar que Java esté instalado correctamente
   - Comprobar que todos los archivos .java estén en la misma carpeta

**¡Disfruta jugando ajedrez! ♟️👑**
