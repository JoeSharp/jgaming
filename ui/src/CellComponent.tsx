import { FC } from 'react';

interface Props {
  value: boolean;
}

const CellComponent: FC<Props> = ({value}) => <td className={value ? 'cell alive' : 'cell dead'}>&nbsp;</td>;

export default CellComponent;
