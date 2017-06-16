import React from 'react';

import EditForm from './EditForm';
import RouteList from './RouteList';

class App extends React.Component {

    constructor() {
        super()

        this.state = {
            editMode: false,
            current: {},
        }
    }

    handleCreate(event) {
        this.setState({editMode: !this.state.editMode})
    }

    render() {
        return (
            <div className='container'>
                {this.state.editMode ? <div className='row'><EditForm value={this.state.current} onBack={this.handleCreate.bind(this)} onSave={this.handleCreate.bind(this)} /></div> : <div className='row'><RouteList onCreate={this.handleCreate.bind(this)}/></div>}
            </div>
        );
    }
}

export default App;