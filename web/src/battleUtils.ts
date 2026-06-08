const SIZE = 12;

export function cellsInRange(x: number, y: number, range: number): Set<string> {
  const cells = new Set<string>();
  for (let cx = 0; cx <= SIZE; cx++) {
    for (let cy = 0; cy <= SIZE; cy++) {
      if (Math.abs(cx - x) <= range && Math.abs(cy - y) <= range) {
        cells.add(`${cx},${cy}`);
      }
    }
  }
  return cells;
}

export function bresenhamLine(x0: number, y0: number, x1: number, y1: number): Array<[number, number]> {
  const points: Array<[number, number]> = [];
  let x = x0;
  let y = y0;
  const dx = Math.abs(x1 - x0);
  const dy = Math.abs(y1 - y0);
  const sx = x0 < x1 ? 1 : -1;
  const sy = y0 < y1 ? 1 : -1;
  let err = dx - dy;

  while (true) {
    points.push([x, y]);
    if (x === x1 && y === y1) break;
    const e2 = 2 * err;
    if (e2 > -dy) {
      err -= dy;
      x += sx;
    }
    if (e2 < dx) {
      err += dx;
      y += sy;
    }
  }
  return points;
}

export function terrainBlocksLos(type: string): boolean {
  return type === 'BUILDING' || type === 'FOREST' || type === 'RUBBLE';
}

export function hasLineOfSight(
  x1: number,
  y1: number,
  x2: number,
  y2: number,
  terrainMap: Map<string, string>
): boolean {
  for (const [x, y] of bresenhamLine(x1, y1, x2, y2)) {
    if (x === x1 && y === y1) continue;
    if (x === x2 && y === y2) continue;
    const t = terrainMap.get(`${x},${y}`);
    if (t && terrainBlocksLos(t)) return false;
  }
  return true;
}

export const MAP_TYPE_LABELS: Record<string, string> = {
  STANDARD: 'Стандарт',
  CITY: 'Город',
  DESERT: 'Пустыня',
  FACTORY: 'Завод',
};

export const TERRAIN_MARK: Record<string, string> = {
  BUILDING: '▧',
  FOREST: '🌲',
  WATER: '≋',
  RUBBLE: '✦',
  OPEN: '',
};
