import React from 'react';
import { GameOfLife } from './types';

import "./gol.css"

interface Props {
  game: GameOfLife;
  deleteGame: () => void;
}

const GameOfLifeComponent: React.FC<Props> = ({game, deleteGame}) => {
  return (<div>
    <button onClick={deleteGame}>Delete Game</button>
    <table className='gol'>
      <tbody>
        {game.game.board.contents.map((row, ri) => (<tr key={ri}>
          {row.map((cell, ci) => <td key={ci} className={cell ? 'cell alive' : 'cell dead'}>&nbsp;</td>)}
        </tr>))}
      </tbody>
    </table>
  </div>)
}

export default GameOfLifeComponent;
