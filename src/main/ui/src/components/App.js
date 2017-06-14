import React from 'react';

import EditForm from './EditForm';
import Header from './Header';
import ToolBar from './ToolBar';
import RouteList from './RouteList';

class App extends React.Component {

    constructor() {
        super()

        this.state = {
            editMode: false,
        }
    }

    handleCreate(event) {
        this.setState({editMode: !this.state.editMode})
    }

    render() {
        return (
            <div className='container'>
                <div className='row'><Header /></div>
                <div className='row'><ToolBar onCreate={this.handleCreate.bind(this)} /></div>
                {this.state.editMode ? <div className='row'><EditForm /></div> : <div className='row'><RouteList /></div>}
            </div>
        );
    }
}

export default App;