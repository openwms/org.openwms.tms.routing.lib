import React from 'react';
import PropTypes from 'prop-types';

import Button from './Button';

class ToolBar extends React.Component {

    render() {
        return (
        <div className="container">
            <button className="btn btn-default" onClick={this.props.onCreate.bind(this)}>{this.props.createMode ? 'Back' : 'Create'}</button>
        </div>);
    }
}

ToolBar.propTypes = {
  onCreate: PropTypes.func.isRequired,
}

export default ToolBar;