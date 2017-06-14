import React from 'react';

import Button from './Button';

class ToolBar extends React.Component {

    handleCreate(event) {
        console.log(event.target)
    }

    render() {
        return (
        <div>
            <Button value='Create' onClick={this.handleCreate.bind(this)}/>
        </div>);
    }
}

export default ToolBar;