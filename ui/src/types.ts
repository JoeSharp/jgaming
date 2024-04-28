export interface GameOfLife {
  id: string,
  game: {
    board: {
      contents: boolean[][],
      previousContents: boolean[][]
    }
  }
}

export interface GameOfLifeApi {
  game: GameOfLife | null;

  createGame(name?: string): void;
  iterateGame(): void;
  deleteGame(): void;
}
