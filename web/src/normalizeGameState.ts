import type { Armor, GameState, Player, Weapon } from './types';

const EMPTY_ARMOR: Armor = { head: 0, body: 0, leftArm: 0, rightArm: 0, legs: 0 };

function normalizeWeapon(w: Partial<Weapon> & Pick<Weapon, 'id' | 'name'>): Weapon {
  return {
    id: w.id,
    catalogKey: w.catalogKey ?? '',
    name: w.name,
    slot: w.slot ?? 'body',
    category: w.category ?? 'GUN',
    ammunition: w.ammunition ?? 0,
    range: w.range ?? 0,
    damage: w.damage ?? 0,
    weight: w.weight ?? 0,
    cost: w.cost ?? 1,
    heat: w.heat ?? 0,
    inRange: w.inRange ?? false,
    hasLineOfSight: w.hasLineOfSight ?? false,
    canFire: w.canFire ?? false,
    disabledReason: w.disabledReason ?? '',
  };
}

function normalizePlayer(p: Partial<Player> & Pick<Player, 'id' | 'name'>): Player {
  const armor = p.armor ?? EMPTY_ARMOR;
  return {
    id: p.id,
    name: p.name,
    mech: p.mech ?? null,
    x: p.x ?? 0,
    y: p.y ?? 0,
    armor,
    maxArmor: p.maxArmor ?? armor,
    heat: p.heat ?? 0,
    radiator: p.radiator ?? 20,
    actionPoints: p.actionPoints ?? 0,
    maxActionPoints: p.maxActionPoints ?? 0,
    weaponWeight: p.weaponWeight ?? 0,
    maxWeight: p.maxWeight ?? 20,
    guns: p.guns ?? 0,
    gunSockets: p.gunSockets ?? 0,
    lasers: p.lasers ?? 0,
    laserSockets: p.laserSockets ?? 0,
    rockets: p.rockets ?? 0,
    rocketSockets: p.rocketSockets ?? 0,
    ready: p.ready ?? false,
    weapons: (p.weapons ?? []).map(normalizeWeapon),
  };
}

/** Заполняет поля, которых может не быть у старой версии сервера */
export function normalizeGameState(raw: GameState): GameState {
  return {
    roomId: raw.roomId,
    phase: raw.phase,
    yourPlayerId: raw.yourPlayerId,
    activePlayerId: raw.activePlayerId ?? null,
    winnerId: raw.winnerId ?? null,
    mapType: raw.mapType ?? 'STANDARD',
    players: (raw.players ?? []).map(normalizePlayer),
    catalog: raw.catalog ?? [],
    terrain: raw.terrain ?? [],
    obstacles: raw.obstacles ?? [],
    log: raw.log ?? [],
  };
}

export function getMePlayer(state: GameState): Player | undefined {
  return state.players.find((p) => p.id === state.yourPlayerId);
}
