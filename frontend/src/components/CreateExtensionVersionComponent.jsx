import React, { Component } from 'react'
import ExtensionVersionService from '../services/ExtensionVersionService';

class CreateExtensionVersionComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
        }
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            ExtensionVersionService.getExtensionVersionById(this.state.id).then( (res) =>{
                let extensionVersion = res.data;
                this.setState({
                });
            });
        }        
    }
    saveOrUpdateExtensionVersion = (e) => {
        e.preventDefault();
        let extensionVersion = {
                extensionVersionId: this.state.id,
            };
        console.log('extensionVersion => ' + JSON.stringify(extensionVersion));

        // step 5
        if(this.state.id === '_add'){
            extensionVersion.extensionVersionId=''
            ExtensionVersionService.createExtensionVersion(extensionVersion).then(res =>{
                this.props.history.push('/extensionVersions');
            });
        }else{
            ExtensionVersionService.updateExtensionVersion(extensionVersion).then( res => {
                this.props.history.push('/extensionVersions');
            });
        }
    }
    

    cancel(){
        this.props.history.push('/extensionVersions');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add ExtensionVersion</h3>
        }else{
            return <h3 className="text-center">Update ExtensionVersion</h3>
        }
    }
    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateExtensionVersion}>Save</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                   </div>
            </div>
        )
    }
}

export default CreateExtensionVersionComponent
