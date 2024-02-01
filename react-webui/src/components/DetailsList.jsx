const DetailsList = ({ data }) => {
  return (
    <div className="flow-root rounded-lg border border-gray-100 py-3 shadow-sm">
      <dl className="-my-3 divide-y divide-gray-100 text-sm">
        <div className="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
          <dt className="font-medium text-gray-900">Id</dt>
          <dd className="text-gray-700 sm:col-span-2">{data.id}</dd>
        </div>
        <div className="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
          <dt className="font-medium text-gray-900">Content</dt>
          <dd className="text-gray-700 sm:col-span-2">
            {data.content ? data.content : "Encrypted"}
          </dd>
        </div>
        <div className="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
          <dt className="font-medium text-gray-900">Encryption Method</dt>
          <dd className="text-gray-700 sm:col-span-2">
            {data.encryption_method}
          </dd>
        </div>
        <div className="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
          <dt className="font-medium text-gray-900">Cipher Text</dt>
          <dd className="text-gray-700 sm:col-span-2">{data.cipher_text}</dd>
        </div>
      </dl>
    </div>
  );
};
export default DetailsList;
