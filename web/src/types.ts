export type GamePhase = 'WAITING' | 'MECH_SELECT' | 'WORKSHOP' | 'BATTLE' | 'FINISHED';

export interface Armor {
  head: number;
  body: number;
  leftArm: number;
  rightArm: number;
  legs: number;
}

export interface TerrainTile {
  x: number;
  y: number;
  type: 'OPEN' | 'BUILDING' | 'FOREST' | 'WATER' | 'RUBBLE';
}

export interface Obstacle {
  x: number;
  y: number;
}

export interface CombatEvent {
  type: string;
  fromX: number;
  fromY: number;
  toX: number;
  toY: number;
  weaponCategory: string;
  hit: boolean;
  critical: boolean;
}

export interface Weapon {
  id: string;
  catalogKey: string;
  name: string;
  slot: string;
  category: string;
  ammunition: number;
  range: number;
  damage: number;
  weight: number;
  cost: number;
  heat: number;
  inRange: boolean;
  hasLineOfSight: boolean;
  canFire: boolean;
  disabledReason: string;
}

export interface WeaponCatalogItem {
  key: string;
  name: string;
  category: string;
  weight: number;
  damage: number;
  range: number;
  heat: number;
  ammunition: number;
  cost: number;
  handMounted: boolean;
}

export interface Player {
  id: string;
  name: string;
  mech: string | null;
  x: number;
  y: number;
  armor: Armor;
  maxArmor: Armor;
  heat: number;
  radiator: number;
  actionPoints: number;
  maxActionPoints: number;
  weaponWeight: number;
  maxWeight: number;
  guns: number;
  gunSockets: number;
  lasers: number;
  laserSockets: number;
  rockets: number;
  rocketSockets: number;
  ready: boolean;
  weapons: Weapon[];
}

export interface GameState {
  roomId: string;
  phase: GamePhase;
  yourPlayerId: string;
  activePlayerId: string | null;
  winnerId: string | null;
  mapType: string;
  players: Player[];
  catalog: WeaponCatalogItem[];
  terrain: TerrainTile[];
  obstacles: Obstacle[];
  log: string[];
}

export type ServerMessage =
  | { type: 'CONNECTED'; playerId: string }
  | { type: 'ROOM_CREATED'; roomId: string; playerId: string }
  | { type: 'ROOM_JOINED'; roomId: string; playerId: string }
  | { type: 'STATE'; state: GameState }
  | { type: 'ACTION_RESULT'; success: boolean; message: string; sound?: string; event?: CombatEvent }
  | { type: 'ERROR'; message: string };

export const MECHS = [
  { id: 'THOR', name: 'Thor', desc: 'Сбалансированный тяжёлый мех' },
  { id: 'MADCAT', name: 'Mad Cat', desc: 'Быстрый и манёвренный' },
  { id: 'LOCUST', name: 'Locust', desc: 'Лёгкий скаут с 7 очками хода' },
  { id: 'ATLAS', name: 'Atlas', desc: 'Танк с мощным тараном' },
] as const;

const wsProtocol = typeof window !== 'undefined' && window.location.protocol === 'https:' ? 'wss:' : 'ws:';
const wsHost = typeof window !== 'undefined' ? window.location.hostname : 'localhost';

/** Локально: ws://localhost:8080/ws; по LAN: ws://192.168.x.x:8080/ws */
export const WS_URL =
  import.meta.env.VITE_WS_URL ?? `${wsProtocol}//${wsHost}:8080/ws`;

export type SoundKey =
  | 'minigun'
  | 'mediumgun'
  | 'biggun'
  | 'smalllaser'
  | 'mediumlaser'
  | 'biglaser'
  | 'rocket'
  | 'step'
  | 'melee'
  | 'hit1'
  | 'hit2'
  | 'warning';
