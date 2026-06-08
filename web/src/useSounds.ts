import { useCallback, useRef } from 'react';
import type { SoundKey } from './types';

const SOUND_FILES: Record<SoundKey, string> = {
  minigun: '/sounds/MiniGun.mp3',
  mediumgun: '/sounds/MiddleGun.mp3',
  biggun: '/sounds/BigGun.mp3',
  smalllaser: '/sounds/smalllaser.mp3',
  mediumlaser: '/sounds/MiddleLaser.mp3',
  biglaser: '/sounds/BigLaser.mp3',
  rocket: '/sounds/rocket.mp3',
  step: '/sounds/Step.mp3',
  melee: '/sounds/taran.mp3',
  hit1: '/sounds/Hit1.mp3',
  hit2: '/sounds/Hit2.mp3',
  warning: '/sounds/warning.mp3',
};

let music: HTMLAudioElement | null = null;
let musicStarted = false;

export function useSounds() {
  const cache = useRef<Map<string, HTMLAudioElement>>(new Map());

  const play = useCallback((key: string | undefined) => {
    if (!key || !(key in SOUND_FILES)) return;
    const soundKey = key as SoundKey;
    let audio = cache.current.get(soundKey);
    if (!audio) {
      audio = new Audio(SOUND_FILES[soundKey]);
      cache.current.set(soundKey, audio);
    }
    audio.currentTime = 0;
    audio.play().catch(() => {});
  }, []);

  const playHitOnMessage = useCallback(
    (message: string) => {
      if (message.includes('Попадание')) {
        play(Math.random() > 0.5 ? 'hit1' : 'hit2');
      }
    },
    [play]
  );

  const startMusic = useCallback(() => {
    if (musicStarted) return;
    if (!music) {
      music = new Audio('/music/Mix.mp3');
      music.loop = true;
      music.volume = 0.35;
    }
    music.play().then(() => {
      musicStarted = true;
    }).catch(() => {});
  }, []);

  const stopMusic = useCallback(() => {
    music?.pause();
    musicStarted = false;
  }, []);

  return { play, playHitOnMessage, startMusic, stopMusic };
}
