import React from 'react';
import PropTypes from 'prop-types';

import Button from './Button';

class ToolBar extends React.Component {

    render() {
        return (
        <div>
            <Button value='Create' onClick={this.props.onCreate.bind(this)}/>
        </div>);
    }
}

ToolBar.propTypes = {
  onCreate: PropTypes.func.isRequired,
}

export default ToolBar;