import * as React from 'react';
import PropTypes from 'prop-types';

class Button extends React.Component {
    render() {
        return (
            <div><button type='button' className='btn btn-default' aria-label='Left Align' onClick={this.props.onClick}><span className={'glyphicon ' + this.props.icon} aria-hidden='true'>{this.props.value}</span></button></div>
        );
    }
};

Button.propTypes = {
  value: PropTypes.string,
  onClick: PropTypes.func,
  icon: PropTypes.string,
};

export default Button;