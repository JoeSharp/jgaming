import { FC, useState, ChangeEventHandler, useCallback } from 'react';

interface Props {
  createGame: (name?: string) => void;
}

const NewGameOfLife: FC<Props> = ({createGame}) => {
  const [name, setName] = useState<string>('');
  const onNameChange: ChangeEventHandler<HTMLInputElement> = useCallback(({target:{value}}) => {
    setName(value);
  }, []);

  return (
    <>
      <button onClick={() => createGame(name)}>Create Game</button>
      <form>
        <label>Prefab Name</label>
        <input type='text' onChange={onNameChange} value={name} />
      </form>
    </>
  );
}

export default NewGameOfLife;
