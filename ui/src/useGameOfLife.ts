import { useState, useCallback } from 'react';
import { GameOfLife, GameOfLifeApi } from './types';

declare global {
  interface Window {
    __backendUrl__: string;
  }
}

function useGameOfLife(): GameOfLifeApi {
  const [game, setGame] = useState<GameOfLife | null>(null);

  console.log('WOBBLE', import.meta.env.VITE_API_HOST);
  console.log('Using Game of Life with Backend', window.__backendUrl__);

  const createGame = useCallback((name?: string) => {
    fetch(`${window.__backendUrl__}/gol/create${name ? '/' + name : ''}`, { method: 'POST', mode: 'cors' })
      .then(r => r.json())
      .then(g => setGame(g));
  }, []);

  const iterateGame = useCallback(() => {
    if (!game) return;
    fetch(`${window.__backendUrl__}/gol/iterate/${game.id}`, { method: 'POST', mode: 'cors' })
      .then(r => r.json())
      .then(g => setGame(g));
  }, [game]);

  const deleteGame = useCallback(() => {
    if (!game) return;
    fetch(`${window.__backendUrl__}/gol/${game.id}`, { method: 'DELETE', mode: 'cors' })
      .then(() => setGame(null));
  }, [game]);

  return {
    game,
    createGame,
    iterateGame,
    deleteGame
  }
}

export default useGameOfLife;
